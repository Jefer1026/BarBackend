package com.jogdev.barbackend.bar.service.impl;

import com.jogdev.barbackend.bar.dto.CreateStockRequest;
import com.jogdev.barbackend.bar.dto.ResponseStock;
import com.jogdev.barbackend.bar.dto.StockDto;
import com.jogdev.barbackend.bar.exception.ObjectNotFoundException;
import com.jogdev.barbackend.bar.mapper.MapperStock;
import com.jogdev.barbackend.bar.persistence.entity.InventoryLocation;
import com.jogdev.barbackend.bar.persistence.entity.Product;
import com.jogdev.barbackend.bar.persistence.entity.Stock;
import com.jogdev.barbackend.bar.persistence.repository.InventoryLocationRepository;
import com.jogdev.barbackend.bar.persistence.repository.StockRepository;
import com.jogdev.barbackend.bar.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final InventoryLocationRepository inventoryLocationRepository;

    @Override
    public Page<ResponseStock> getAllStocks(Pageable pageable) {
        return stockRepository.findAll(pageable).map(MapperStock::toResponseStock);
    }

    @Override
    public Optional<Stock> getStockById(int id) {
        return stockRepository.findById(id);
    }


    @Override
    public Stock updateStock(StockDto stockDto, int stockId) {
        Stock stock = getStockById(stockId).orElseThrow(() -> new ObjectNotFoundException("Stock Not Found"));
        int currentQuantity = stock.getQuantity();
        if (stockDto.getQuantity() != null) {


            stock.setQuantity(currentQuantity + stockDto.getQuantity());

        }
        if (stockDto.getMinQuantity() != null) {
            stock.setMinQuantity(stockDto.getMinQuantity());
        }

        return stockRepository.save(stock);
    }

    @Override
    public Stock createStock(CreateStockRequest request) {
        Product product = new Product();
        product.setProductId(request.getProductId());

        System.out.println("product id: " + product.getProductId());


        InventoryLocation location = inventoryLocationRepository
                .findInventoryLocationByLocationName(request.getLocationCode())
                .orElseThrow(() -> new RuntimeException("Ubicación no encontrada"));

        System.out.println("Location: " + location.getLocationName());


        Optional<Stock> existing = stockRepository.findByProductAndLocation(product, location);
        if (existing.isPresent()) {
            throw new RuntimeException("Ya existe un stock para ese producto y ubicación");
        }

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setLocation(location);
        stock.setQuantity(request.getQuantity() != null ? request.getQuantity() : 0);
        stock.setMinQuantity(request.getMinQuantity() != null ? request.getMinQuantity() : 0);

        System.out.println("stock: " + stock.getProduct().getProductId() + " " + stock.getLocation().getLocationName());


        return stockRepository.save(stock);

    }


}
