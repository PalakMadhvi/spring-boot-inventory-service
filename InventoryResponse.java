package com.example.inventory.dto;

public record InventoryResponse(
        Long productId, String sku, String productName,
        Long warehouseId, String warehouseCode, String warehouseName,
        Integer quantity, boolean lowStock
) {}
