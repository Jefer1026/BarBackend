package com.jogdev.barbackend.bar.service.impl;


import com.jogdev.barbackend.bar.dto.SaleItemRequest;
import com.jogdev.barbackend.bar.exception.ObjectNotFoundException;
import com.jogdev.barbackend.bar.persistence.entity.*;
import com.jogdev.barbackend.bar.persistence.repository.InventoryLocationRepository;
import com.jogdev.barbackend.bar.persistence.repository.InvoiceRepository;
import com.jogdev.barbackend.bar.persistence.repository.ProductRepository;
import com.jogdev.barbackend.bar.persistence.repository.StockRepository;
import com.jogdev.barbackend.bar.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final ProductRepository productRepository;
    private final StockRepository stockRepository;
    private final InvoiceRepository invoiceRepository;
    private final InventoryLocationRepository inventoryLocationRepository;


    @Override
    public Page<Invoice> getAllInvoices(Pageable pageable) {

        return invoiceRepository.findAll(pageable);
    }

    @Override
    public Invoice generateInvoice(List<SaleItemRequest> saleItems) {

        Invoice invoice = new Invoice();
        invoice.setDate(LocalDateTime.now());

        List<InvoiceDetail> invoiceDetailList = new ArrayList<>();
        BigDecimal totalInvoice = BigDecimal.ZERO;

        InvoiceDetail detail = null;
        for (SaleItemRequest saleItem : saleItems) {
            Product product = productRepository.findById(saleItem.getProductId())
                    .orElseThrow(() -> new ObjectNotFoundException("Product not found"));

            InventoryLocation location = inventoryLocationRepository.findInventoryLocationByLocationName("RECEPTION")
                    .orElseThrow(() -> new ObjectNotFoundException("Inventory location not found"));


            Optional<Stock> existing = stockRepository.findByProductAndLocation(product, location);
            if (existing.isEmpty()) {
                throw new RuntimeException("no existe stock para ese producto y ubicación");
            }

            if (existing.get().getQuantity() < saleItem.getQuantity()) {
                throw new IllegalArgumentException("There aren't enough stock for " + product.getProductName());
            }

            existing.get().setQuantity(existing.get().getQuantity() - saleItem.getQuantity());
            stockRepository.save(existing.get());


            BigDecimal totalPrice = product.getProductPrice().multiply(new BigDecimal(saleItem.getQuantity()));


            detail = new InvoiceDetail();
            detail.setProductName(product.getProductName());
            detail.setQuantitySold(saleItem.getQuantity());
            detail.setUnitPrice(product.getProductPrice());
            detail.setTotalPrice(totalPrice);
            detail.setProduct(product);

            detail.setInvoice(invoice);

            invoiceDetailList.add(detail);
            totalInvoice = totalInvoice.add(totalPrice);
        }

        invoice.setItems(invoiceDetailList);
        invoice.setTotal(totalInvoice);


        invoiceRepository.save(invoice);

        return invoice;
    }
}