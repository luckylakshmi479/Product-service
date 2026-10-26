package org.example.productservice.consumer;

import org.example.productservice.event.ProductCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Component;


    @Component
    public class AuditConsumer {
        @RetryableTopic(
                attempts = "3")
        @KafkaListener(
                topics = "product-order-topic",
                groupId = "Audit-group-v3")
        public void consume(ProductCreatedEvent event) {

            System.out.println(
                    "AUDIT CONSUMER -> " + event);
            throw new RuntimeException(
                    "Testing Failure");
        }
    }



