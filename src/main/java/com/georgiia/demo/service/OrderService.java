package com.georgiia.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.georgiia.demo.dto.OrderDTO;
import com.georgiia.demo.dto.OrderItemDTO;
import com.georgiia.demo.entity.Customer;
import com.georgiia.demo.entity.Order;
import com.georgiia.demo.entity.OrderItem;
import com.georgiia.demo.entity.Product;
import com.georgiia.demo.repository.CustomerRepository;
import com.georgiia.demo.repository.OrderRepository;
import com.georgiia.demo.repository.ProductRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, CustomerRepository customerRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(o -> {
                    List<OrderItemDTO> items = o.getItems() != null
                            ? o.getItems().stream()
                                    .map(i -> new OrderItemDTO(i.getId(), i.getProduct().getId(), i.getQuantity(), i.getProduct().getPrice()))
                                    .collect(Collectors.toList())
                            : new ArrayList<>();
                    return new OrderDTO(o.getId(), o.getOrderDate(), o.getCustomer().getId(), items);
                })
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Order o = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        List<OrderItemDTO> items = o.getItems() != null
                ? o.getItems().stream()
                        .map(i -> new OrderItemDTO(i.getId(), i.getProduct().getId(), i.getQuantity(), i.getProduct().getPrice()))
                        .collect(Collectors.toList())
                : new ArrayList<>();
        return new OrderDTO(o.getId(), o.getOrderDate(), o.getCustomer().getId(), items);
    }

    public OrderDTO createOrder(OrderDTO dto) {
        Order order = new Order();

        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        order.setCustomer(customer);

        if (dto.getItems() != null && !dto.getItems().isEmpty()) {
            List<OrderItem> items = new ArrayList<>();
            for (OrderItemDTO itemDTO : dto.getItems()) {
                Product product = productRepository.findById(itemDTO.getProductId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                OrderItem item = new OrderItem(order, product, itemDTO.getQuantity());
                items.add(item);
            }
            order.setItems(items);
        }

        Order saved = orderRepository.save(order);
        List<OrderItemDTO> itemDTOs = saved.getItems().stream()
                .map(i -> new OrderItemDTO(i.getId(), i.getProduct().getId(), i.getQuantity(), i.getProduct().getPrice()))
                .collect(Collectors.toList());
        return new OrderDTO(saved.getId(), saved.getOrderDate(), saved.getCustomer().getId(), itemDTOs);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
