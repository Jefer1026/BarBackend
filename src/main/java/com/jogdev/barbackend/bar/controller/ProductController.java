package com.jogdev.barbackend.bar.controller;

import com.jogdev.barbackend.bar.dto.ProductDto;
import com.jogdev.barbackend.bar.persistence.entity.Product;
import com.jogdev.barbackend.bar.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<Page<Product>> findAllProducts(
            @ParameterObject
            @PageableDefault(size = 10, page = 0, sort = "productName")
            Pageable pageable) {
        Page<Product> productPage = productService.findAllProducts(pageable);

        return productPage.hasContent() ? ResponseEntity.ok(productPage)
                : ResponseEntity.notFound().build();


    }

    @GetMapping()
    public ResponseEntity<Page<Product>> findAllProductsTrue(
            @ParameterObject
            @PageableDefault(size = 10, page = 0, sort = "productName")
            Pageable pageable) {
        Page<Product> productPage = productService.findAllProductsByProductStatusTrue(pageable);

        return productPage.hasContent() ? ResponseEntity.ok(productPage)
                : ResponseEntity.notFound().build();


    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> findProductById(@PathVariable Integer productId) {

        return productService.findProductById(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductDto productDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createOneProduct(productDto));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId, @RequestBody ProductDto productDto) {

        return ResponseEntity.ok(productService.updateOneProduct(productDto, productId));
    }

    @PutMapping("/{productId}/disabled")
    public ResponseEntity<Product> updateProductDisabled(@PathVariable Integer productId) {
        return ResponseEntity.ok(productService.disableOneProduct(productId));
    }

    @PutMapping("/{productId}/enabled")
    public ResponseEntity<Product> updateProductEnabled(@PathVariable Integer productId) {
        return ResponseEntity.ok(productService.enableOneProduct(productId));
    }
}
