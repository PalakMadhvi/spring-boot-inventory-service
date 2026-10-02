package com.example.inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "inventory",
       uniqueConstraints = @UniqueConstraint(name = "uk_inventory_product_warehouse",
                                             columnNames = {"product_id", "warehouse_id"}))
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    @Column(nullable = false)
    private Integer quantity = 0;

    @Version
    private Long version;

    public Long getId() { return id; }
    public Product getProduct() { return product; }
    public Warehouse getWarehouse() { return warehouse; }
    public Integer getQuantity() { return quantity; }
    public Long getVersion() { return version; }

    public void setId(Long id) { this.id = id; }
    public void setProduct(Product product) { this.product = product; }
    public void setWarehouse(Warehouse warehouse) { this.warehouse = warehouse; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public void setVersion(Long version) { this.version = version; }
}
