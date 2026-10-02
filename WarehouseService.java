package com.example.inventory.service;

import com.example.inventory.dto.*;
import com.example.inventory.entity.Warehouse;
import com.example.inventory.exception.*;
import com.example.inventory.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WarehouseService {
    private final WarehouseRepository repo;

    public WarehouseService(WarehouseRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<WarehouseResponse> findAll() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public WarehouseResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Transactional
    public WarehouseResponse create(WarehouseRequest request) {
        if (repo.existsByCodeIgnoreCase(request.code())) {
            throw new BusinessException("Warehouse code already exists: " + request.code());
        }
        Warehouse w = new Warehouse();
        apply(w, request);
        return toResponse(repo.save(w));
    }

    @Transactional
    public WarehouseResponse update(Long id, WarehouseRequest request) {
        Warehouse w = get(id);
        repo.findByCodeIgnoreCase(request.code()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) throw new BusinessException("Warehouse code already exists: " + request.code());
        });
        apply(w, request);
        return toResponse(repo.save(w));
    }

    @Transactional
    public void delete(Long id) {
        repo.delete(get(id));
    }

    private Warehouse get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Warehouse not found: " + id));
    }

    private void apply(Warehouse w, WarehouseRequest r) {
        w.setCode(r.code().trim().toUpperCase());
        w.setName(r.name().trim());
        w.setLocation(r.location().trim());
    }

    private WarehouseResponse toResponse(Warehouse w) {
        return new WarehouseResponse(w.getId(), w.getCode(), w.getName(), w.getLocation());
    }
}
