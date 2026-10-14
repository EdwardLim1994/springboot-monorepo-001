package dev.learning.orders.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import dev.learning.orders.models.Order;

@Component
public class OrderEvents {
    private final KafkaTemplate<String, String> kafka;

    public OrderEvents(KafkaTemplate<String, String> kafka) {
        this.kafka = kafka;
    }

    public void publish(Order order) {
        kafka.send("order-created", order.getId(),
                order.getId() + "," + order.getProductId() + "," + order.getQuantity());
    }
}
