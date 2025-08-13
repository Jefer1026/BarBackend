package com.jogdev.barbackend.bar.controller;

import com.jogdev.barbackend.bar.dto.ProductSalesReportDto;
import com.jogdev.barbackend.bar.dto.SaleItemRequest;
import com.jogdev.barbackend.bar.persistence.entity.Invoice;
import com.jogdev.barbackend.bar.service.SaleService;
import com.jogdev.barbackend.bar.service.impl.SalesReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Parameter;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;
    private final SalesReportService salesReportService;

    @GetMapping
    public ResponseEntity<Page<Invoice>> getAllSaleItems(Pageable pageable) {

        Page<Invoice> invoicePage = saleService.getAllInvoices(pageable);

        System.out.println("lengt invoice =" + invoicePage.getTotalElements());


        return invoicePage.hasContent() ? ResponseEntity.ok().body(invoicePage) : ResponseEntity.notFound().build();
    }


    @GetMapping("/product-sales")
    public ResponseEntity<ProductSalesReportDto> getProductSalesReport(
            @RequestParam String productName,

            @Parameter(description = "Start date in format yyyy-MM-dd")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "End date in format yyyy-MM-dd")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        ProductSalesReportDto report = salesReportService.getSalesReport(productName, startDate, endDate);

        if (report == null || report.getTotalQuantitySold() == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(report);
    }

    @PostMapping("/invoice")
    public ResponseEntity<Invoice> createInvoice(@RequestBody List<SaleItemRequest> saleItems) {

        Invoice invoice = saleService.generateInvoice(saleItems);
        return ResponseEntity.ok(invoice);
    }


}