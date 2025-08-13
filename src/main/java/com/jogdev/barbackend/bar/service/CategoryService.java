package com.jogdev.barbackend.bar.service;

import com.jogdev.barbackend.bar.dto.CategoryDto;
import com.jogdev.barbackend.bar.persistence.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CategoryService {

    Page<Category> findAllCategoriesStatusTrue(Pageable pageable);

    Page<Category> findAllCategories(Pageable pageable);

    Optional<Category> findCategoryById(Integer categoryId);

    Category createOneCategory(CategoryDto categoryDto);

    Category updateOneCategory(CategoryDto categoryDto, Integer categoryId);

    Category disableOneCategory(Integer categoryId);

    Category enableOneCategory(Integer categoryId);
}
