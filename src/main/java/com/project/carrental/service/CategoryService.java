package com.project.carrental.service;

import com.project.carrental.dto.request.CategoryRequest;
import com.project.carrental.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    Category createCategory(CategoryRequest categoryRequest);
    Category updateCategory(Long categoryId, CategoryRequest categoryRequest);
    void deleteCategory(Long categoryId);
}
