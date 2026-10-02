package com.example.inventory.controller;

import com.example.inventory.dto.*;
import com.example.inventory.service.WarehouseService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/warehouses")
public class WarehouseController {
    private final WarehouseService service;

    public WarehouseController(WarehouseService service) { this.service = service; }

    @GetMapping
    public List<WarehouseResponse> all() { return service.findAll(); }

    @GetMapping("/{id}")
    public WarehouseResponse one(@PathVariable Long id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<WarehouseResponse> create(@Valid @RequestBody WarehouseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public WarehouseResponse update(@PathVariable Long id, @Valid @RequestBody WarehouseRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}
