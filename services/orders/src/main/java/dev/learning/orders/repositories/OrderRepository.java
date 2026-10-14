package dev.learning.orders.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.learning.orders.models.Order;

public interface OrderRepository extends JpaRepository<Order, String> {
}
