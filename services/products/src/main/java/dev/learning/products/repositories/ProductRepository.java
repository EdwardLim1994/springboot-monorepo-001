package dev.learning.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.learning.products.models.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}
