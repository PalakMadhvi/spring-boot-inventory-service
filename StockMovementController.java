package com.example.inventory.controller;

import com.example.inventory.dto.*;
import com.example.inventory.service.StockMovementService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementController {
    private final StockMovementService service;

    public StockMovementController(StockMovementService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<StockMovementResponse> move(@Valid @RequestBody StockMovementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.move(request));
    }

    @GetMapping
    public List<StockMovementResponse> all() { return service.all(); }

    @GetMapping("/product/{productId}")
    public List<StockMovementResponse> byProduct(@PathVariable Long productId) {
        return service.byProduct(productId);
    }

    @GetMapping("/warehouse/{warehouseId}")
    public List<StockMovementResponse> byWarehouse(@PathVariable Long warehouseId) {
        return service.byWarehouse(warehouseId);
    }
}
