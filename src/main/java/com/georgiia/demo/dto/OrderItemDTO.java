package com.georgiia.demo.dto;

public class OrderItemDTO {
    private Long id;
    private Long productId;
    private Integer quantity;
    private Double price;

    public OrderItemDTO() {}

    public OrderItemDTO(Long id, Long productId, Integer quantity, Double price) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }
}
