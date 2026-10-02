package com.example.inventory.service;

import com.example.inventory.dto.InventoryResponse;
import com.example.inventory.entity.*;
import com.example.inventory.exception.NotFoundException;
import com.example.inventory.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository inventoryRepo;
    private final ProductRepository productRepo;
    private final WarehouseRepository warehouseRepo;

    public InventoryService(InventoryRepository inventoryRepo, ProductRepository productRepo,
                            WarehouseRepository warehouseRepo) {
        this.inventoryRepo = inventoryRepo;
        this.productRepo = productRepo;
        this.warehouseRepo = warehouseRepo;
    }

    @Transactional(readOnly = true)
    public InventoryResponse get(Long productId, Long warehouseId) {
        return toResponse(inventoryRepo.findByProductIdAndWarehouseId(productId, warehouseId)
                .orElseThrow(() -> new NotFoundException("Inventory record not found")));
    }

    @Transactional(readOnly = true)
    public List<InventoryResponse> findAll() {
        return inventoryRepo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional
    public Inventory getOrCreateForUpdate(Long productId, Long warehouseId) {
        return inventoryRepo.findForUpdate(productId, warehouseId).orElseGet(() -> {
            Product p = productRepo.findById(productId)
                    .orElseThrow(() -> new NotFoundException("Product not found: " + productId));
            Warehouse w = warehouseRepo.findById(warehouseId)
                    .orElseThrow(() -> new NotFoundException("Warehouse not found: " + warehouseId));
            Inventory i = new Inventory();
            i.setProduct(p);
            i.setWarehouse(w);
            i.setQuantity(0);
            return inventoryRepo.save(i);
        });
    }

    private InventoryResponse toResponse(Inventory i) {
        return new InventoryResponse(
                i.getProduct().getId(), i.getProduct().getSku(), i.getProduct().getName(),
                i.getWarehouse().getId(), i.getWarehouse().getCode(), i.getWarehouse().getName(),
                i.getQuantity(), i.getQuantity() <= i.getProduct().getReorderLevel()
        );
    }
}
