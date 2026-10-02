package com.example.inventory.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "warehouses", uniqueConstraints = @UniqueConstraint(name = "uk_warehouse_code", columnNames = "code"))
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String code;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 200)
    private String location;

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public String getLocation() { return location; }

    public void setId(Long id) { this.id = id; }
    public void setCode(String code) { this.code = code; }
    public void setName(String name) { this.name = name; }
    public void setLocation(String location) { this.location = location; }
}
