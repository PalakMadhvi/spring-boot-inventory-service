package com.example.inventory.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank @Size(max = 50) String sku,
        @NotBlank @Size(max = 150) String name,
        @Size(max = 500) String description,
        @NotNull @DecimalMin("0.00") BigDecimal price,
        @NotNull @Min(0) Integer reorderLevel,
        Boolean active
) {}
