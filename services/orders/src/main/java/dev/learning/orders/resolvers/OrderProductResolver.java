package dev.learning.orders.resolvers;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.federation.EntityMapping;
import org.springframework.stereotype.Controller;

import dev.learning.orders.models.Order;
import dev.learning.orders.models.ProductReference;

@Controller
public class OrderProductResolver {
    @EntityMapping
    public ProductReference product(@Argument String id) {
        return new ProductReference(id);
    }

    @SchemaMapping(typeName = "Order", field = "product")
    public ProductReference product(Order order) {
        return new ProductReference(order.getProductId());
    }
}
