package com.jogdev.barbackend.bar.service.impl;

import com.jogdev.barbackend.bar.dto.InventoryLocationDto;
import com.jogdev.barbackend.bar.exception.ObjectNotFoundException;
import com.jogdev.barbackend.bar.persistence.entity.InventoryLocation;
import com.jogdev.barbackend.bar.persistence.entity.Product;
import com.jogdev.barbackend.bar.persistence.entity.Stock;
import com.jogdev.barbackend.bar.persistence.repository.InventoryLocationRepository;
import com.jogdev.barbackend.bar.persistence.repository.ProductRepository;
import com.jogdev.barbackend.bar.persistence.repository.StockRepository;
import com.jogdev.barbackend.util.StatusObject;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final ProductRepository productRepository;
    private final InventoryLocationRepository inventoryLocationRepository;
    private final StockRepository stockRepository;


    @Transactional
    public void transferStock(int productId, String fromLocationName, String toLocationName, int quantity) {


        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));


        InventoryLocation fromLocation = inventoryLocationRepository.findInventoryLocationByLocationName(fromLocationName)
                .orElseThrow(() -> new ObjectNotFoundException("location not found"));



        InventoryLocation toLocation = inventoryLocationRepository.findInventoryLocationByLocationName(toLocationName)
                .orElseThrow(() -> new ObjectNotFoundException("location not found"));



        Stock fromStock = stockRepository.findByProductAndLocation(product, fromLocation)
                .orElseThrow(() -> new ObjectNotFoundException("stock not found"));


        if (fromStock.getQuantity() < quantity) {
            throw new RuntimeException("There aren't enough stocks for this product");
        }

        fromStock.setQuantity(fromStock.getQuantity() - quantity);


        Stock toStock = stockRepository.findByProductAndLocation(product, toLocation)
                .orElseGet(() -> {

                    Stock newStock = new Stock();
                    newStock.setProduct(product);
                    newStock.setLocation(toLocation);
                    newStock.setQuantity(0);
                    return newStock;

                });

        toStock.setQuantity(toStock.getQuantity() + quantity);

        stockRepository.save(fromStock);
        stockRepository.save(toStock);

    }

    public int getTotalStock(int productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        return Optional.ofNullable(stockRepository.getTotalQuantity(product)).orElse(0);
    }

    public int getStockAtLocation(int productId, String locationName) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        InventoryLocation location = inventoryLocationRepository.findInventoryLocationByLocationName(locationName)
                .orElseThrow(() -> new ObjectNotFoundException("location not found"));

        return stockRepository.findByProductAndLocation(product, location)
                .map(Stock::getQuantity)
                .orElse(0);

    }


    public InventoryLocation createInventoryLocation(@RequestBody InventoryLocationDto inventoryLocationDto) {

        InventoryLocation inventoryLocation = new InventoryLocation();
        inventoryLocation.setLocationName(inventoryLocationDto.getLocationName().toUpperCase());
        inventoryLocation.setStatus(StatusObject.ENABLED);

        return inventoryLocationRepository.save(inventoryLocation);
    }

    public InventoryLocation updateInventoryLocation(InventoryLocationDto inventoryLocationDto, int locationId) {
        InventoryLocation inventoryLocation = inventoryLocationRepository.findById(locationId)
                .orElseThrow(() -> new ObjectNotFoundException("location not found"));
        inventoryLocation.setLocationName(inventoryLocationDto.getLocationName().toUpperCase());
        return inventoryLocationRepository.save(inventoryLocation);
    }

    public InventoryLocation disableInventoryLocation(int locationId) {
        InventoryLocation inventoryLocation = inventoryLocationRepository.findById(locationId)
                .orElseThrow(() -> new ObjectNotFoundException("location not found"));
        inventoryLocation.setStatus(StatusObject.DISABLED);
        return inventoryLocationRepository.save(inventoryLocation);

    }
    public Page<InventoryLocation> findInventoryLocationByStatus(StatusObject status, Pageable pageable) {
        return inventoryLocationRepository.findInventoryLocationByStatusIs(status, pageable);
    }



    public Page<InventoryLocation> getAllInventoryLocations(Pageable pageable) {
        return inventoryLocationRepository.findAll(pageable);
    }








}
