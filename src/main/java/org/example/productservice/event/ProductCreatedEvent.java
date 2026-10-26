package org.example.productservice.event;

public record ProductCreatedEvent(
        Long productId,
        String productName

) {
}
