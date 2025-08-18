package com.jogdev.barbackend.bar.controller;

import com.jogdev.barbackend.bar.dto.CategoryDto;
import com.jogdev.barbackend.bar.persistence.entity.Category;
import com.jogdev.barbackend.bar.service.CategoryService;
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
@RequestMapping("categories")
public class CategoryController {


    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<Page<Category>> findAllCategories(
            @ParameterObject
            @PageableDefault(size = 10, page = 0, sort = "categoryName")
            Pageable pageable) {
        Page<Category> categoryPage = categoryService.findAllCategories(pageable);

        return categoryPage.hasContent() ? ResponseEntity.ok(categoryPage)
                : ResponseEntity.notFound().build();

    }

    @GetMapping
    public ResponseEntity<Page<Category>> findAllCategoriesTrue(
            @ParameterObject
            @PageableDefault(size = 10, page = 0, sort = "categoryName")
            Pageable pageable) {
        Page<Category> categoryPage = categoryService.findAllCategoriesStatusTrue(pageable);

        return categoryPage.hasContent() ? ResponseEntity.ok(categoryPage)
                : ResponseEntity.notFound().build();
    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Integer categoryId) {

        return categoryService.findCategoryById(categoryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoryService.createOneCategory(categoryDto));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Integer categoryId, @RequestBody CategoryDto categoryDto) {

        return ResponseEntity.ok(categoryService.updateOneCategory(categoryDto, categoryId));
    }

    @PutMapping("/{categoryId}/disabled")
    public ResponseEntity<Category> updateCategoryDisabled(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(categoryService.disableOneCategory(categoryId));
    }

    @PutMapping("/{categoryId}/enabled")
    public ResponseEntity<Category> updateCategoryEnabled(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(categoryService.enableOneCategory(categoryId));
    }
}

