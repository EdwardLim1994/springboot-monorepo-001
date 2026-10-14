package dev.learning.orders.controllers;

import java.util.List;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import dev.learning.orders.kafka.OrderEvents;
import dev.learning.orders.models.Order;
import dev.learning.orders.repositories.OrderRepository;

@Controller
public class OrderController {
    private final OrderRepository orders;
    private final OrderEvents events;

    public OrderController(OrderRepository orders, OrderEvents events) {
        this.orders = orders;
        this.events = events;
    }

    @QueryMapping
    public List<Order> orders() {
        return orders.findAll();
    }

    @MutationMapping
    public Order createOrder(@Argument String productId, @Argument int quantity) {
        Order order = orders.save(new Order(java.util.UUID.randomUUID().toString(), productId, quantity));
        events.publish(order);
        return order;
    }
}
