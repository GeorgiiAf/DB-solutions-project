package com.georgiia.demo.controller;

import com.georgiia.demo.dto.SupplierDTO;
import com.georgiia.demo.service.SupplierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public List<SupplierDTO> getAll() {
        return supplierService.getAllSuppliers();
    }

    @GetMapping("/{id}")
    public SupplierDTO getById(@PathVariable Long id) {
        return supplierService.getSupplierById(id);
    }

    @PostMapping
    public SupplierDTO create(@RequestBody SupplierDTO supplier) {
        return supplierService.createSupplier(supplier);
    }

    @PutMapping("/{id}")
    public SupplierDTO update(@PathVariable Long id, @RequestBody SupplierDTO supplier) {
        return supplierService.updateSupplier(id, supplier);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        supplierService.deleteSupplier(id);
    }
}