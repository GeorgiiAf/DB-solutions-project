package com.georgiia.demo.controller;

import com.georgiia.demo.entity.Customer;
import com.georgiia.demo.repository.CustomerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }


    @GetMapping
    public List<Customer> getAll() {
        return repo.findAll();
    }

    // GET
    @GetMapping("/{id}")
    public Customer getById(@PathVariable Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    // POST
    @PostMapping
    public Customer create(@RequestBody Customer customer) {
        return repo.save(customer);
    }

    // PUT
    @PutMapping("/{id}")
    public Customer update(@PathVariable Long id, @RequestBody Customer customer) {
        Customer existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("customer not found with id " + id));

        existing.setFirstName(customer.getFirstName());
        existing.setLastName(customer.getLastName());
        existing.setEmail(customer.getEmail());
        existing.setCustomersPhone(customer.getCustomersPhone());
        return repo.save(existing);
    }

    // DELETE /products/{id}
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}