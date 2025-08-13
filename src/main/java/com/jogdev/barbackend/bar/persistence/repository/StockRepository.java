package com.jogdev.barbackend.bar.persistence.repository;

import com.jogdev.barbackend.bar.persistence.entity.InventoryLocation;
import com.jogdev.barbackend.bar.persistence.entity.Product;
import com.jogdev.barbackend.bar.persistence.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {

    Optional<Stock> findByProductAndLocation(Product product, InventoryLocation location);

    Optional<Stock> findStockByProduct_ProductId(Integer productId);

    @Query("SELECT SUM(s.quantity) FROM Stock s WHERE s.product = :product")
    Integer getTotalQuantity(Product product);

}