package org.example.productservice.dto;

public record RegisterRequest(
        String username,
        String password
) {
}