package com.georgiia.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    private Long id;
    private LocalDateTime orderDate;
    private Long customerId;
    private List<OrderItemDTO> items;

    public OrderDTO() {}

    public OrderDTO(Long id, LocalDateTime orderDate, Long customerId, List<OrderItemDTO> items) {
        this.id = id;
        this.orderDate = orderDate;
        this.customerId = customerId;
        this.items = items;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public List<OrderItemDTO> getItems() { return items; }
    public void setItems(List<OrderItemDTO> items) { this.items = items; }
}
