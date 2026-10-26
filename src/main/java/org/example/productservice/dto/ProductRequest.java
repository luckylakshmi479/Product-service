package org.example.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
        @NotBlank(message = "Product name is required")
        String name,
        @NotBlank(message = "Description is required")
        String description,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")

        Double price,

        @NotNull(message = "Stock is required")
        @Positive(message = "Stock must be positive")
        Integer stock

) {
}
