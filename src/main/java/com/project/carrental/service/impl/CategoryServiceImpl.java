package com.project.carrental.service.impl;

import com.project.carrental.dto.request.CategoryRequest;
import com.project.carrental.model.Category;
import com.project.carrental.repository.CategoryRepository;
import com.project.carrental.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category createCategory(CategoryRequest categoryRequest){
        return categoryRepository.save(Category.builder().name(categoryRequest.getName()).build());
    }

    @Override
    public Category updateCategory(Long categoryId, CategoryRequest categoryRequest){
        Category category = categoryRepository.findById(categoryId).get();

        category.setName(categoryRequest.getName());

        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId).get();

        categoryRepository.delete(category);
    }
}
