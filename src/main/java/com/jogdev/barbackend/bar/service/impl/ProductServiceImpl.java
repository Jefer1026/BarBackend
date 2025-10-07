package com.jogdev.barbackend.bar.service.impl;

import com.jogdev.barbackend.bar.dto.ProductDto;
import com.jogdev.barbackend.bar.persistence.entity.Category;
import com.jogdev.barbackend.bar.persistence.entity.Product;
import com.jogdev.barbackend.bar.persistence.repository.ProductRepository;
import com.jogdev.barbackend.bar.service.CategoryService;
import com.jogdev.barbackend.bar.service.ProductService;
import com.jogdev.barbackend.util.StatusObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    @Override
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findAllProductsByProductStatusTrue(Pageable pageable) {
        return productRepository.findAllProductsByProductStatusLike(StatusObject.ENABLED, pageable);
    }

    @Override
    public Optional<Product> findProductById(Integer productId) {
        return productRepository.findById(productId);
    }

    @Override
    public Product createOneProduct(ProductDto productDto) {

        Product product = new Product();
        product.setProductName(productDto.getProductName().toUpperCase());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductCost(productDto.getProductCost());

        Category category = new Category();
        category.setCategoryId(productDto.getCategoryId());
        product.setCategory(category);

        product.setProductName(productDto.getProductName().toUpperCase());
        product.setProductPrice(productDto.getProductPrice());
        product.setProductStatus(StatusObject.ENABLED);



        return productRepository.save(product);
    }

    @Override
    public Product updateOneProduct(ProductDto productDto, Integer productId) {

        Product productFromDb = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productFromDb.setProductName(productDto.getProductName().toUpperCase());
        productFromDb.setProductPrice(productDto.getProductPrice());
        productFromDb.setProductCost(productDto.getProductCost());
        Category category = new Category();
        category.setCategoryId(productDto.getCategoryId());
        productFromDb.setCategory(category);


        return productRepository.save(productFromDb);
    }

    @Override
    public Product disableOneProduct(Integer productId) {

        Product productFromDb = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productFromDb.setProductStatus(StatusObject.DISABLED);
        return productRepository.save(productFromDb);
    }

    @Override
    public Product enableOneProduct(Integer productId) {
        Product productFromDb = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productFromDb.setProductStatus(StatusObject.ENABLED);
        return productRepository.save(productFromDb);
    }
}

