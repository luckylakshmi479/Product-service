package org.example.productservice.dto;

public record ProductResponse(
        Long id,
        String name,
        String description,
        Double price,
        Integer stock

) {
}
