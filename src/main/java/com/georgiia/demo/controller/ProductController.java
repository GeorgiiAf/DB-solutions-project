package com.georgiia.demo.controller;

import com.georgiia.demo.entity.Product;
import com.georgiia.demo.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository repo;

    public ProductController(ProductRepository repo) {
        this.repo = repo;
    }

    // GET /products
    @GetMapping
    public List<Product> getAll() {
        return repo.findAll();
    }

    // GET /products/{id}
    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    // POST /products
    @PostMapping
    public Product create(@RequestBody Product product) {
        return repo.save(product);
    }

    // PUT /products/{id}
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        Product existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setStockQuantity(product.getStockQuantity());
        return repo.save(existing);
    }

    // DELETE /products/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}