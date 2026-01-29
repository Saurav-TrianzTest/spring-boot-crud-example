package com.javatechie.crud.example.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product();
    }

    @Test
    public void testNoArgsConstructor() {
        Product prod = new Product();
        assertNotNull(prod);
    }

    @Test
    public void testAllArgsConstructor() {
        Product prod = new Product(1, "Test Product", 10, 99.99);
        assertEquals(1, prod.getId());
        assertEquals("Test Product", prod.getName());
        assertEquals(10, prod.getQuantity());
        assertEquals(99.99, prod.getPrice());
    }

    @Test
    public void testSetAndGetId() {
        product.setId(100);
        assertEquals(100, product.getId());
    }

    @Test
    public void testSetAndGetIdWithZero() {
        product.setId(0);
        assertEquals(0, product.getId());
    }

    @Test
    public void testSetAndGetIdWithNegative() {
        product.setId(-1);
        assertEquals(-1, product.getId());
    }

    @Test
    public void testSetAndGetName() {
        product.setName("Laptop");
        assertEquals("Laptop", product.getName());
    }

    @Test
    public void testSetAndGetNameWithNull() {
        product.setName(null);
        assertNull(product.getName());
    }

    @Test
    public void testSetAndGetNameWithEmpty() {
        product.setName("");
        assertEquals("", product.getName());
    }

    @Test
    public void testSetAndGetNameWithSpecialCharacters() {
        product.setName("Product-@#$%");
        assertEquals("Product-@#$%", product.getName());
    }

    @Test
    public void testSetAndGetQuantity() {
        product.setQuantity(50);
        assertEquals(50, product.getQuantity());
    }

    @Test
    public void testSetAndGetQuantityWithZero() {
        product.setQuantity(0);
        assertEquals(0, product.getQuantity());
    }

    @Test
    public void testSetAndGetQuantityWithNegative() {
        product.setQuantity(-10);
        assertEquals(-10, product.getQuantity());
    }

    @Test
    public void testSetAndGetQuantityWithLargeValue() {
        product.setQuantity(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, product.getQuantity());
    }

    @Test
    public void testSetAndGetPrice() {
        product.setPrice(199.99);
        assertEquals(199.99, product.getPrice());
    }

    @Test
    public void testSetAndGetPriceWithZero() {
        product.setPrice(0.0);
        assertEquals(0.0, product.getPrice());
    }

    @Test
    public void testSetAndGetPriceWithNegative() {
        product.setPrice(-50.0);
        assertEquals(-50.0, product.getPrice());
    }

    @Test
    public void testSetAndGetPriceWithVerySmallValue() {
        product.setPrice(0.01);
        assertEquals(0.01, product.getPrice());
    }

    @Test
    public void testSetAndGetPriceWithLargeValue() {
        product.setPrice(999999.99);
        assertEquals(999999.99, product.getPrice());
    }

    @Test
    public void testProductEquals() {
        Product prod1 = new Product(1, "Phone", 5, 499.99);
        Product prod2 = new Product(1, "Phone", 5, 499.99);
        assertEquals(prod1, prod2);
    }

    @Test
    public void testProductNotEquals() {
        Product prod1 = new Product(1, "Phone", 5, 499.99);
        Product prod2 = new Product(2, "Tablet", 3, 299.99);
        assertNotEquals(prod1, prod2);
    }

    @Test
    public void testProductHashCode() {
        Product prod1 = new Product(1, "Phone", 5, 499.99);
        Product prod2 = new Product(1, "Phone", 5, 499.99);
        assertEquals(prod1.hashCode(), prod2.hashCode());
    }

    @Test
    public void testProductToString() {
        Product prod = new Product(1, "Mouse", 10, 25.50);
        String toString = prod.toString();
        assertNotNull(toString);
        assertTrue(toString.contains("Mouse"));
        assertTrue(toString.contains("1"));
    }

    @Test
    public void testSetAllFields() {
        product.setId(5);
        product.setName("Keyboard");
        product.setQuantity(20);
        product.setPrice(75.00);

        assertEquals(5, product.getId());
        assertEquals("Keyboard", product.getName());
        assertEquals(20, product.getQuantity());
        assertEquals(75.00, product.getPrice());
    }

    @Test
    public void testProductWithMaxIntId() {
        product.setId(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, product.getId());
    }

    @Test
    public void testProductWithMinIntId() {
        product.setId(Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, product.getId());
    }

    @Test
    public void testProductWithMaxDoublePrice() {
        product.setPrice(Double.MAX_VALUE);
        assertEquals(Double.MAX_VALUE, product.getPrice());
    }
}
