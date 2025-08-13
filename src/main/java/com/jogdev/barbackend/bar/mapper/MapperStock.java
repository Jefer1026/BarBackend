package com.jogdev.barbackend.bar.mapper;

import com.jogdev.barbackend.bar.dto.ResponseStock;
import com.jogdev.barbackend.bar.persistence.entity.Product;
import com.jogdev.barbackend.bar.persistence.entity.Stock;

import java.time.format.DateTimeFormatter;

public class MapperStock {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ResponseStock toResponseStock(Stock stock) {

        Product product = stock.getProduct();


        return new ResponseStock(
                stock.getId(),
                stock.getQuantity(),
                stock.getMinQuantity(),
                stock.getCreationDate().format(formatter),
                stock.getLastUpdate().format(formatter),
                stock.getLocation().getLocationName(),
                product.getProductId(),
                product.getProductName(),
                product.getProductPrice()
        );

    }
}
