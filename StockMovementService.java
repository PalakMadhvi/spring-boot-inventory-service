package com.example.inventory.service;

import com.example.inventory.dto.*;
import com.example.inventory.entity.*;
import com.example.inventory.exception.*;
import com.example.inventory.repository.StockMovementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StockMovementService {
    private final StockMovementRepository movementRepo;
    private final InventoryService inventoryService;

    public StockMovementService(StockMovementRepository movementRepo, InventoryService inventoryService) {
        this.movementRepo = movementRepo;
        this.inventoryService = inventoryService;
    }

    @Transactional
    public StockMovementResponse move(StockMovementRequest request) {
        Inventory inventory = inventoryService.getOrCreateForUpdate(request.productId(), request.warehouseId());

        int current = inventory.getQuantity();
        int updated = request.type() == MovementType.IN
                ? current + request.quantity()
                : current - request.quantity();

        if (updated < 0) {
            throw new BusinessException("Insufficient stock. Available: " + current +
                    ", requested: " + request.quantity());
        }

        inventory.setQuantity(updated);

        StockMovement movement = new StockMovement();
        movement.setProduct(inventory.getProduct());
        movement.setWarehouse(inventory.getWarehouse());
        movement.setType(request.type());
        movement.setQuantity(request.quantity());
        movement.setReference(request.reference());
        movement.setReason(request.reason());

        return toResponse(movementRepo.save(movement));
    }

    @Transactional(readOnly = true)
    public List<StockMovementResponse> all() {
        return movementRepo.findAllByOrderByCreatedAtDesc().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<StockMovementResponse> byProduct(Long productId) {
        return movementRepo.findByProductIdOrderByCreatedAtDesc(productId).stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<StockMovementResponse> byWarehouse(Long warehouseId) {
        return movementRepo.findByWarehouseIdOrderByCreatedAtDesc(warehouseId).stream().map(this::toResponse).toList();
    }

    private StockMovementResponse toResponse(StockMovement m) {
        return new StockMovementResponse(
                m.getId(), m.getProduct().getId(), m.getProduct().getSku(),
                m.getWarehouse().getId(), m.getWarehouse().getCode(), m.getType(),
                m.getQuantity(), m.getReference(), m.getReason(), m.getCreatedAt()
        );
    }
}
