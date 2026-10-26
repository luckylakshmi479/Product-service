package org.example.productservice.dto;

public record LoginRequest(
        String username,
        String password
) {
}
