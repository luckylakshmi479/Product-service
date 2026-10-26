package org.example.productservice.service;

import lombok.RequiredArgsConstructor;
import org.example.productservice.event.ProductCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductProducer {

    private final KafkaTemplate<String,ProductCreatedEvent > kafkaTemplate;

    public void publish(ProductCreatedEvent event) {

        kafkaTemplate.send(
                "product-order-topic",event.productId().toString(),
                event);

    }
}
