package com.project.carrental.controller;

import com.project.carrental.dto.request.CategoryRequest;
import com.project.carrental.model.Category;
import com.project.carrental.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    @CrossOrigin(origins = "http://localhost:5173")
    public ResponseEntity<List<Category>> getCategories(){
        return new ResponseEntity<>(categoryService.getCategories(), HttpStatus.OK);
    }

    @PostMapping("/categories")
    ResponseEntity<Category> createCategory(@RequestBody @Valid CategoryRequest categoryRequest){
        return new ResponseEntity<>(categoryService.createCategory(categoryRequest), HttpStatus.CREATED);
    }

    @PutMapping("/categories/{categoryId}")
    ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody @Valid CategoryRequest categoryRequest){
        return new ResponseEntity<>(categoryService.updateCategory(categoryId, categoryRequest), HttpStatus.OK);
    }

    @DeleteMapping("/categories/{categoryId}")
    ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Category id: " + categoryId + " deleted successfully", HttpStatus.OK);
    }
}
