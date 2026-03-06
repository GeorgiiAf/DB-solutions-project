package com.georgiia.demo.controller;

import com.georgiia.demo.dto.CustomerDTO;
import com.georgiia.demo.entity.CompanyCustomer;
import com.georgiia.demo.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDTO> getAll() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public CustomerDTO create(@RequestBody CustomerDTO customer) {
        return customerService.createCustomer(customer);
    }

    @PostMapping("/company")
    public CustomerDTO createCompanyCustomer(@RequestBody CompanyCustomer companyCustomer) {
        return customerService.createCompanyCustomer(companyCustomer);
    }

    @PutMapping("/{id}")
    public CustomerDTO update(@PathVariable Long id, @RequestBody CustomerDTO customer) {
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}