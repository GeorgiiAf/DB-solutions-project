package com.georgiia.demo.controller;

import com.georgiia.demo.entity.Supplier;
import com.georgiia.demo.repository.SupplierRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierRepository repo;

    public SupplierController(SupplierRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Supplier> getAll() { return repo.findAll(); }

    @GetMapping("/{id}")
    public Supplier getById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    @PostMapping
    public Supplier create(@RequestBody Supplier supplier) {
        return repo.save(supplier);
    }

    @PutMapping("/{id}")
    public Supplier update(@PathVariable Long id, @RequestBody Supplier supplier) {
        Supplier existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        existing.setName(supplier.getName());
        existing.setEmail(supplier.getEmail());
        existing.setPhone(supplier.getPhone());
        return repo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}