package com.example.inventory.service;

import com.example.inventory.dto.*;
import com.example.inventory.entity.Product;
import com.example.inventory.exception.*;
import com.example.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return repo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse findById(Long id) {
        return toResponse(get(id));
    }

    @Transactional
    public ProductResponse create(ProductRequest request) {
        if (repo.existsBySkuIgnoreCase(request.sku())) {
            throw new BusinessException("SKU already exists: " + request.sku());
        }
        Product p = new Product();
        apply(p, request);
        return toResponse(repo.save(p));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest request) {
        Product p = get(id);
        repo.findBySkuIgnoreCase(request.sku()).ifPresent(existing -> {
            if (!existing.getId().equals(id)) throw new BusinessException("SKU already exists: " + request.sku());
        });
        apply(p, request);
        return toResponse(repo.save(p));
    }

    @Transactional
    public void delete(Long id) {
        Product p = get(id);
        repo.delete(p);
    }

    private Product get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Product not found: " + id));
    }

    private void apply(Product p, ProductRequest r) {
        p.setSku(r.sku().trim().toUpperCase());
        p.setName(r.name().trim());
        p.setDescription(r.description());
        p.setPrice(r.price());
        p.setReorderLevel(r.reorderLevel());
        p.setActive(r.active() == null || r.active());
    }

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(p.getId(), p.getSku(), p.getName(), p.getDescription(),
                p.getPrice(), p.getReorderLevel(), p.getActive());
    }
}
