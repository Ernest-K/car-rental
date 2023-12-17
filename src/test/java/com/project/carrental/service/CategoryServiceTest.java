package com.project.carrental.service;

import com.project.carrental.dto.request.CategoryRequest;
import com.project.carrental.dto.response.CarDetailResponse;
import com.project.carrental.model.Category;
import com.project.carrental.repository.CategoryRepository;
import com.project.carrental.service.impl.CategoryServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void getCategoriesShouldReturnCategories() {
        when(categoryRepository.findAll()).thenReturn(List.of(new Category()));

        List<Category> categories = categoryService.getCategories();

        assertThat(categories).isNotEmpty();
        assertThat(categories.get(0)).isNotNull();
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void createCategoryShouldReturnCategory() {
        CategoryRequest categoryRequest = createSampleCategoryRequest();
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());

        Category category = categoryService.createCategory(categoryRequest);

        assertThat(category).isNotNull();
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void updateCategoryShouldReturnCategory() {
        Long categoryId = 1L;
        CategoryRequest categoryRequest = createSampleCategoryRequest();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(new Category()));
        when(categoryRepository.save(any(Category.class))).thenReturn(new Category());

        Category category = categoryService.updateCategory(categoryId, categoryRequest);

        assertThat(category).isNotNull();
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void updateCategoryShouldThrowEntityNotFoundExceptionWhenWrongCategoryId() {
        Long categoryId = 1L;
        CategoryRequest categoryRequest = createSampleCategoryRequest();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            Category category = categoryService.updateCategory(categoryId, categoryRequest);
        }).isInstanceOf(EntityNotFoundException.class);

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void deleteCategoryShouldDeleteCar() {
        long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(new Category()));

        assertThatNoException().isThrownBy(() -> {
            categoryService.deleteCategory(categoryId);
        });

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).delete(any(Category.class));
    }

    @Test
    void deleteCategoryShouldThrowEntityNotFoundExceptionWhenWrongCategoryId() {
        long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> {
            categoryService.deleteCategory(categoryId);
        }).isInstanceOf(EntityNotFoundException.class);

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).delete(any(Category.class));
    }

    private CategoryRequest createSampleCategoryRequest() {
        return new CategoryRequest("SUV");
    }
}