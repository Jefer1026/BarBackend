package com.jogdev.barbackend.bar.service.impl;

import com.jogdev.barbackend.bar.dto.CategoryDto;
import com.jogdev.barbackend.bar.exception.ObjectNotFoundException;
import com.jogdev.barbackend.bar.persistence.entity.Category;
import com.jogdev.barbackend.bar.persistence.repository.CategoryRepository;
import com.jogdev.barbackend.bar.service.CategoryService;
import com.jogdev.barbackend.util.StatusObject;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAllCategoriesStatusTrue(Pageable pageable) {
        return categoryRepository.findAllByCategoryStatusLike(StatusObject.ENABLED, pageable);
    }

    @Override
    public Page<Category> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Optional<Category> findCategoryById(Integer categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Category createOneCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setCategoryName(categoryDto.getCategoryName().toUpperCase());
        category.setCategoryStatus(StatusObject.ENABLED);

        return categoryRepository.save(category);
    }

    @Override
    public Category updateOneCategory(CategoryDto categoryDto, Integer categoryId) {

        Category categoryFromDb = categoryRepository.findById(categoryId).orElseThrow((() -> new ObjectNotFoundException("Category not found")));
        categoryFromDb.setCategoryName(categoryDto.getCategoryName().toUpperCase());

        return categoryRepository.save(categoryFromDb);
    }

    @Override
    public Category disableOneCategory(Integer categoryId) {
        Category categoryFromDb = categoryRepository.findById(categoryId).orElseThrow((() -> new ObjectNotFoundException("Category not found")));
        categoryFromDb.setCategoryStatus(StatusObject.DISABLED);
        return categoryRepository.save(categoryFromDb);
    }

    @Override
    public Category enableOneCategory(Integer categoryId) {
        Category categoryFromDb = categoryRepository.findById(categoryId).orElseThrow((() -> new ObjectNotFoundException("Category not found")));
        categoryFromDb.setCategoryStatus(StatusObject.ENABLED);
        return categoryRepository.save(categoryFromDb);
    }
}

