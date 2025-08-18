package com.jogdev.barbackend.bar.controller;

import com.jogdev.barbackend.bar.dto.*;
import com.jogdev.barbackend.bar.mapper.MapperStock;
import com.jogdev.barbackend.bar.persistence.entity.InventoryLocation;
import com.jogdev.barbackend.bar.persistence.entity.Stock;
import com.jogdev.barbackend.bar.persistence.repository.InventoryLocationRepository;
import com.jogdev.barbackend.bar.service.StockService;
import com.jogdev.barbackend.bar.service.impl.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {


    private final StockService stockService;
    private final InventoryService inventoryService;
    private final InventoryLocationRepository inventoryLocationRepository;

    @GetMapping
    public ResponseEntity<Page<ResponseStock>> getAllStocks(
            @ParameterObject
            @PageableDefault(size = 10, page = 0, sort = "id")
            Pageable pageable) {

        Page<ResponseStock> stocksPage = stockService.getAllStocks(pageable);

        return stocksPage.hasContent() ? ResponseEntity.ok(stocksPage) : ResponseEntity.notFound().build();
    }

    @GetMapping("/inventory-location")
    public ResponseEntity<Page<InventoryLocation>> getAllInventoryLocation(
            @ParameterObject
            @PageableDefault(size = 10, page = 0, sort = "id")
            Pageable pageable) {

        Page<InventoryLocation> inventoryLocationPage = inventoryLocationRepository.findAll(pageable);

        return inventoryLocationPage.hasContent() ? ResponseEntity.ok(inventoryLocationPage) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{stockById}")
    public ResponseEntity<Stock> getStockById(@PathVariable int stockById) {

        return stockService.getStockById(stockById)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    @PutMapping("/{stockId}")
    public ResponseEntity<Stock> updateStock(@RequestBody StockDto stockDto, @PathVariable int stockId) {

        return ResponseEntity.status(HttpStatus.OK).body(stockService.updateStock(stockDto, stockId));
    }


    @PostMapping("/transfer")
    public ResponseEntity<String> transferStock(@RequestBody StockTransferRequest request) {
        try {
            inventoryService.transferStock(
                    request.getProductId(),
                    request.getFromLocation(),
                    request.getToLocation(),
                    request.getQuantity()
            );
            return ResponseEntity.ok("Transferencia realizada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/location")
    public ResponseEntity<Integer> getStockAtLocation(
            @RequestParam int productId,
            @RequestParam String locationCode
    ) {
        try {
            int quantity = inventoryService.getStockAtLocation(productId, locationCode);
            return ResponseEntity.ok(quantity);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(0);
        }
    }

    @GetMapping("/total")
    public ResponseEntity<Integer> getTotalStock(@RequestParam int productId) {
        try {
            int total = inventoryService.getTotalStock(productId);
            return ResponseEntity.ok(total);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(0);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseStock> createStock(@RequestBody CreateStockRequest request) {
        try {
            Stock stock = stockService.createStock(request);
            return ResponseEntity.ok(MapperStock.toResponseStock(stock));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/inventory-location")
    public ResponseEntity<InventoryLocation> createInventoryLocation(@RequestBody InventoryLocationDto inventoryLocationDto) {


        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventoryLocation(inventoryLocationDto));
    }


}