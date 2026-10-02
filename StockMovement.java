package com.example.inventory.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_movements")
public class StockMovement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private MovementType type;

    @Column(nullable = false)
    private Integer quantity;

    @Column(length = 100)
    private String reference;

    @Column(length = 250)
    private String reason;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public Warehouse getWarehouse() { return warehouse; }
    public MovementType getType() { return type; }
    public Integer getQuantity() { return quantity; }
    public String getReference() { return reference; }
    public String getReason() { return reason; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setProduct(Product product) { this.product = product; }
    public void setWarehouse(Warehouse warehouse) { this.warehouse = warehouse; }
    public void setType(MovementType type) { this.type = type; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setReference(String reference) { this.reference = reference; }
    public void setReason(String reason) { this.reason = reason; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
