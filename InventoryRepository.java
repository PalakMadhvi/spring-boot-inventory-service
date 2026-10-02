package com.example.inventory.repository;

import com.example.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("select i from Inventory i join fetch i.product p join fetch i.warehouse w where p.id = :productId and w.id = :warehouseId")
    Optional<Inventory> findForUpdate(@Param("productId") Long productId, @Param("warehouseId") Long warehouseId);

    Optional<Inventory> findByProductIdAndWarehouseId(Long productId, Long warehouseId);
}
