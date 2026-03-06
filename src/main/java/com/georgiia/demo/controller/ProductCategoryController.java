package com.georgiia.demo.controller;

import com.georgiia.demo.dto.ProductCategoryDTO;
import com.georgiia.demo.service.ProductCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class ProductCategoryController {

    private final ProductCategoryService categoryService;

    public ProductCategoryController(ProductCategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<ProductCategoryDTO> getAll() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    public ProductCategoryDTO getById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @PostMapping
    public ProductCategoryDTO create(@RequestBody ProductCategoryDTO category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public ProductCategoryDTO update(@PathVariable Long id, @RequestBody ProductCategoryDTO category) {
        return categoryService.updateCategory(id, category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}