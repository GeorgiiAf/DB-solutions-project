package com.georgiia.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.georgiia.demo.dto.SupplierDTO;
import com.georgiia.demo.entity.Supplier;
import com.georgiia.demo.repository.SupplierRepository;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(s -> new SupplierDTO(s.getId(), s.getName(), s.getEmail(), s.getPhone()))
                .collect(Collectors.toList());
    }

    public SupplierDTO getSupplierById(Long id) {
        Supplier s = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        return new SupplierDTO(s.getId(), s.getName(), s.getEmail(), s.getPhone());
    }

    public SupplierDTO createSupplier(SupplierDTO dto) {
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setEmail(dto.getEmail());
        supplier.setPhone(dto.getPhone());
        Supplier saved = supplierRepository.save(supplier);
        return new SupplierDTO(saved.getId(), saved.getName(), saved.getEmail(), saved.getPhone());
    }

    public SupplierDTO updateSupplier(Long id, SupplierDTO dto) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        supplier.setName(dto.getName());
        supplier.setEmail(dto.getEmail());
        supplier.setPhone(dto.getPhone());
        Supplier updated = supplierRepository.save(supplier);
        return new SupplierDTO(updated.getId(), updated.getName(), updated.getEmail(), updated.getPhone());
    }

    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);
    }
}
