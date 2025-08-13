package com.jogdev.barbackend.bar.persistence.repository;

import com.jogdev.barbackend.bar.persistence.entity.Product;
import com.jogdev.barbackend.util.StatusObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Page<Product> findAllProductsByProductStatusLike(StatusObject status, Pageable pageable);
}