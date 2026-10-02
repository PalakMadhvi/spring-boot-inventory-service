package com.example.inventory.controller;

import com.example.inventory.dto.InventoryResponse;
import com.example.inventory.service.InventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService service;

    public InventoryController(InventoryService service) { this.service = service; }

    @GetMapping
    public List<InventoryResponse> all() { return service.findAll(); }

    @GetMapping("/{productId}/{warehouseId}")
    public InventoryResponse one(@PathVariable Long productId, @PathVariable Long warehouseId) {
        return service.get(productId, warehouseId);
    }
}
