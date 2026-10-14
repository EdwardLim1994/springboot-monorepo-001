package dev.learning.products.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import dev.learning.products.models.Product;
import dev.learning.products.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    @Mock
    ProductRepository products;

    @InjectMocks
    ProductController controller;

    @Test
    void productsReturnsAllSavedProducts() {
        List<Product> saved = List.of(new Product("book-1", "Book", 19.99));
        when(products.findAll()).thenReturn(saved);

        assertSame(saved, controller.products());
    }

    @Test
    void createProductPersistsAndReturnsProduct() {
        when(products.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product created = controller.createProduct("book-1", "Book", 19.99);

        ArgumentCaptor<Product> saved = ArgumentCaptor.forClass(Product.class);
        verify(products).save(saved.capture());
        assertSame(saved.getValue(), created);
        assertEquals("book-1", created.getId());
        assertEquals("Book", created.getName());
        assertEquals(19.99, created.getPrice());
    }

    @Test
    void federationLookupReturnsMatchingProduct() {
        Product product = new Product("book-1", "Book", 19.99);
        when(products.findById("book-1")).thenReturn(Optional.of(product));

        assertSame(product, controller.product("book-1"));
    }
}
