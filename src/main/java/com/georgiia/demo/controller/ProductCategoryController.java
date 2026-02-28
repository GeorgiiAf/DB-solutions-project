package com.georgiia.demo.controller;

import com.georgiia.demo.entity.ProductCategory;
import com.georgiia.demo.repository.ProductCategoryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class ProductCategoryController {

    private final ProductCategoryRepository repo;

    public ProductCategoryController(ProductCategoryRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<ProductCategory> getAll() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ProductCategory getById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @PostMapping
    public ProductCategory create(@RequestBody ProductCategory category) {
        return repo.save(category);
    }

    @PutMapping("/{id}")
    public ProductCategory update(@PathVariable Long id,
                                  @RequestBody ProductCategory category) {

        ProductCategory existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setName(category.getName());
        existing.setDescription(category.getDescription());

        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}