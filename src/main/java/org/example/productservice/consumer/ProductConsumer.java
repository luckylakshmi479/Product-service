package org.example.productservice.consumer;

import org.example.productservice.event.ProductCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

    @RetryableTopic(
            attempts = "3")
    @KafkaListener(
            topics = "product-partition-topic",
            groupId = "product-group-v3")
    public void consume(
            ProductCreatedEvent event) {

        System.out.println(
                "product consumer" +
                        "Received: " + event);
        throw new RuntimeException(
                "Testing Failure");
    }
}
