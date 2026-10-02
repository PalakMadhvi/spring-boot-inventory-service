package com.example.inventory.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products", uniqueConstraints = @UniqueConstraint(name = "uk_product_sku", columnNames = "sku"))
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String sku;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer reorderLevel;

    @Column(nullable = false)
    private Boolean active = true;

    public Long getId() { return id; }
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public Integer getReorderLevel() { return reorderLevel; }
    public Boolean getActive() { return active; }

    public void setId(Long id) { this.id = id; }
    public void setSku(String sku) { this.sku = sku; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public void setReorderLevel(Integer reorderLevel) { this.reorderLevel = reorderLevel; }
    public void setActive(Boolean active) { this.active = active; }
}
