package com.jogdev.barbackend.bar.service;


import com.jogdev.barbackend.bar.dto.ProductDto;
import com.jogdev.barbackend.bar.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductService {
    Page<Product> findAllProducts(Pageable pageable);

    Page<Product> findAllProductsByProductStatusTrue(Pageable pageable);

    Optional<Product> findProductById(Integer productId);

    Product createOneProduct(ProductDto productDto);

    Product updateOneProduct(ProductDto productDto, Integer productId);

    Product disableOneProduct(Integer productId);

    Product enableOneProduct(Integer productId);
}


