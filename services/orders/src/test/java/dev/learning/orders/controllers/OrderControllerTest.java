package dev.learning.orders.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import dev.learning.orders.kafka.OrderEvents;
import dev.learning.orders.models.Order;
import dev.learning.orders.repositories.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @Mock
    OrderRepository orders;

    @Mock
    OrderEvents events;

    @InjectMocks
    OrderController controller;

    @Test
    void ordersReturnsAllSavedOrders() {
        List<Order> saved = List.of(new Order("order-1", "book-1", 2));
        when(orders.findAll()).thenReturn(saved);

        assertSame(saved, controller.orders());
    }

    @Test
    void createOrderPersistsAndPublishesCreatedOrder() {
        when(orders.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order created = controller.createOrder("book-1", 2);

        ArgumentCaptor<Order> saved = ArgumentCaptor.forClass(Order.class);
        verify(orders).save(saved.capture());
        verify(events).publish(saved.getValue());
        assertSame(saved.getValue(), created);
        assertNotNull(created.getId());
        assertEquals("book-1", created.getProductId());
        assertEquals(2, created.getQuantity());
    }
}
