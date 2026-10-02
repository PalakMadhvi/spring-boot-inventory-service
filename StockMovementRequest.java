package com.example.inventory.dto;

import com.example.inventory.entity.MovementType;
import jakarta.validation.constraints.*;

public record StockMovementRequest(
        @NotNull Long productId,
        @NotNull Long warehouseId,
        @NotNull MovementType type,
        @NotNull @Min(1) Integer quantity,
        @Size(max = 100) String reference,
        @Size(max = 250) String reason
) {}
