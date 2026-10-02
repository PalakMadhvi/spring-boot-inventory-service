package com.example.inventory.repository;

import com.example.inventory.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
    Optional<Warehouse> findByCodeIgnoreCase(String code);
    boolean existsByCodeIgnoreCase(String code);
}
