package dev.learning.orders.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {
    @KafkaListener(topics = "order-created", groupId = "orders-learning")
    public void onOrderCreated(String event) {
        System.out.println("Consumed order-created event: " + event);
    }
}
