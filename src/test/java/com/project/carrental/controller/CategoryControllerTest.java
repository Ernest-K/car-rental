package com.project.carrental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.carrental.dto.request.CategoryRequest;
import com.project.carrental.model.Category;
import com.project.carrental.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

    @MockBean
    private CategoryServiceImpl categoryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCategoriesShouldReturnCategories() throws Exception {
        List<Category> categories = List.of(createSampleCategory(), createSampleCategory());
        when(categoryService.getCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    void createCategoryShouldReturnCategory() throws Exception {
        CategoryRequest categoryRequest = new CategoryRequest("SUV");
        Category category = Category.builder().name("SUV").build();
        when(categoryService.createCategory(categoryRequest)).thenReturn(category);

        mockMvc.perform(post("/api/categories")
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("SUV"));
    }

    @Test
    void createCategoryShouldThrowUnauthorizedException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CategoryRequest categoryRequest = new CategoryRequest("SUV");
        Category category = Category.builder().name("SUV").build();

        String request = objectMapper.writeValueAsString(categoryRequest);

        when(categoryService.createCategory(categoryRequest)).thenReturn(category);

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void updateCategoryShouldReturnCategory() throws Exception {
        Long categoryId = 1L;
        CategoryRequest categoryRequest = new CategoryRequest("SUV");
        Category category = new Category(categoryId, "SUV");
        when(categoryService.updateCategory(categoryId, categoryRequest)).thenReturn(category);

        mockMvc.perform(put("/api/categories/{categoryId}", categoryId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("SUV"));
    }

    @Test
    void updateCategoryShouldThrowUnauthorizedException() throws Exception {
        Long categoryId = 1L;
        CategoryRequest categoryRequest = new CategoryRequest("SUV");
        Category category = new Category(categoryId, "SUV");
        when(categoryService.updateCategory(categoryId, categoryRequest)).thenReturn(category);

        mockMvc.perform(put("/api/categories/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void deleteCategoryShouldDeleteCategory() throws Exception {
        Long categoryId = 1L;
        String successMessage = "Category id: " + categoryId + " deleted successfully";

        mockMvc.perform(delete("/api/categories/{categoryId}", categoryId)
                        .with(SecurityMockMvcRequestPostProcessors.user("admin").roles("ADMIN")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(successMessage));

        verify(categoryService, times(1)).deleteCategory(categoryId);

    }

    @Test
    void deleteCategoryShouldThrowUnauthorizedException() throws Exception {
        Long categoryId = 1L;

        mockMvc.perform(delete("/api/categories/{categoryId}", categoryId))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());

        verifyNoInteractions(categoryService);
    }

    private Category createSampleCategory(){
        return Category.builder().name("SUV").build();
    }
}