package com.example.inventory.dto;

import com.example.inventory.entity.MovementType;
import java.time.LocalDateTime;

public record StockMovementResponse(
        Long id, Long productId, String sku, Long warehouseId, String warehouseCode,
        MovementType type, Integer quantity, String reference, String reason,
        LocalDateTime createdAt
) {}
