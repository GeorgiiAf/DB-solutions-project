package com.georgiia.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.georgiia.demo.dto.ProductDTO;
import com.georgiia.demo.entity.Product;
import com.georgiia.demo.entity.ProductCategory;
import com.georgiia.demo.repository.ProductCategoryRepository;
import com.georgiia.demo.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, ProductCategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(p -> new ProductDTO(p.getId(), p.getName(), p.getDescription(), p.getPrice(),
                p.getStockQuantity(), p.getCategory() != null ? p.getCategory().getId() : null))
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductDTO(p.getId(), p.getName(), p.getDescription(), p.getPrice(),
                p.getStockQuantity(), p.getCategory() != null ? p.getCategory().getId() : null);
    }

    public ProductDTO createProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());

        if (dto.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        Product saved = productRepository.save(product);
        return new ProductDTO(saved.getId(), saved.getName(), saved.getDescription(), saved.getPrice(),
                saved.getStockQuantity(), saved.getCategory() != null ? saved.getCategory().getId() : null);
    }

    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());

        if (dto.getCategoryId() != null) {
            ProductCategory category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }

        Product updated = productRepository.save(product);
        return new ProductDTO(updated.getId(), updated.getName(), updated.getDescription(), updated.getPrice(),
                updated.getStockQuantity(), updated.getCategory() != null ? updated.getCategory().getId() : null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
