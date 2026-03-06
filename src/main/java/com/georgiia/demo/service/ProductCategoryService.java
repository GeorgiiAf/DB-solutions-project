package com.georgiia.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.georgiia.demo.dto.ProductCategoryDTO;
import com.georgiia.demo.entity.ProductCategory;
import com.georgiia.demo.repository.ProductCategoryRepository;

@Service
public class ProductCategoryService {

    private final ProductCategoryRepository categoryRepository;

    public ProductCategoryService(ProductCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<ProductCategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(c -> new ProductCategoryDTO(c.getId(), c.getName(), c.getDescription()))
                .collect(Collectors.toList());
    }

    public ProductCategoryDTO getCategoryById(Long id) {
        ProductCategory c = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return new ProductCategoryDTO(c.getId(), c.getName(), c.getDescription());
    }

    public ProductCategoryDTO createCategory(ProductCategoryDTO dto) {
        ProductCategory category = new ProductCategory();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        ProductCategory saved = categoryRepository.save(category);
        return new ProductCategoryDTO(saved.getId(), saved.getName(), saved.getDescription());
    }

    public ProductCategoryDTO updateCategory(Long id, ProductCategoryDTO dto) {
        ProductCategory category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        ProductCategory updated = categoryRepository.save(category);
        return new ProductCategoryDTO(updated.getId(), updated.getName(), updated.getDescription());
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
