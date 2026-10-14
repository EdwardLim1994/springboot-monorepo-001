package dev.learning.products.controllers;

import java.util.List;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.federation.EntityMapping;
import org.springframework.stereotype.Controller;

import dev.learning.products.models.Product;
import dev.learning.products.repositories.ProductRepository;

@Controller
public class ProductController {
    private final ProductRepository products;

    public ProductController(ProductRepository products) {
        this.products = products;
    }

    @QueryMapping
    public List<Product> products() {
        return products.findAll();
    }

    @MutationMapping
    public Product createProduct(@Argument String id, @Argument String name, @Argument double price) {
        return products.save(new Product(id, name, price));
    }

    @EntityMapping
    public Product product(@Argument String id) {
        return products.findById(id).orElse(null);
    }
}
