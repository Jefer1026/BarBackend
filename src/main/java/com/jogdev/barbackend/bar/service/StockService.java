package com.jogdev.barbackend.bar.service;


import com.jogdev.barbackend.bar.dto.CreateStockRequest;
import com.jogdev.barbackend.bar.dto.ResponseStock;
import com.jogdev.barbackend.bar.dto.StockDto;
import com.jogdev.barbackend.bar.persistence.entity.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface StockService {

    Page<ResponseStock> getAllStocks(Pageable pageable);

    Optional<Stock> getStockById(int id);

    Stock updateStock(StockDto stockDto, int stockId);

    Stock createStock(CreateStockRequest request);


}