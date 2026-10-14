package dev.learning.orders.kafka;

import static org.mockito.Mockito.verify;

import dev.learning.orders.models.Order;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;

class OrderEventsTest {
    @SuppressWarnings("unchecked")
    private final KafkaTemplate<String, String> kafka = org.mockito.Mockito.mock(KafkaTemplate.class);

    private final OrderEvents events = new OrderEvents(kafka);

    @Test
    void publishSendsOrderCreatedEvent() {
        Order order = new Order("order-1", "book-1", 2);

        events.publish(order);

        verify(kafka).send("order-created", "order-1", "order-1,book-1,2");
    }
}
