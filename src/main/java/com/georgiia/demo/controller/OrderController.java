package com.georgiia.demo.controller;

import com.georgiia.demo.entity.Order;
import com.georgiia.demo.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository repo;

    public OrderController(OrderRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Order> getAll() { return repo.findAll(); }

    @GetMapping("/{id}")
    public Order getById(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @PostMapping
    public Order create(@RequestBody Order order) {
        return repo.save(order);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { repo.deleteById(id); }
}