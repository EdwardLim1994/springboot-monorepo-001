package dev.learning.orders.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@jakarta.persistence.Table(name = "orders")
public class Order {
    @Id
    private String id;
    private String productId;
    private int quantity;

    protected Order() {
    }

    public Order(String id, String productId, int quantity) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
