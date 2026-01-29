package com.javatechie.crud.example.controller;

import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController controller;

    private Product product;
    private Product product2;
    private List<Product> productList;

    @BeforeEach
    public void setUp() {
        product = new Product(1, "Laptop", 10, 999.99);
        product2 = new Product(2, "Mouse", 50, 25.50);
        productList = Arrays.asList(product, product2);
    }

    @Test
    public void testConstructor() {
        ProductController testController = new ProductController(service);
        assertNotNull(testController);
    }

    @Test
    public void testAddProduct() {
        when(service.saveProduct(any(Product.class))).thenReturn(product);

        Product result = controller.addProduct(product);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(1, result.getId());
        verify(service, times(1)).saveProduct(product);
    }

    @Test
    public void testAddProductWithNullName() {
        Product nullNameProduct = new Product(3, null, 5, 100.0);
        when(service.saveProduct(nullNameProduct)).thenReturn(nullNameProduct);

        Product result = controller.addProduct(nullNameProduct);

        assertNull(result.getName());
        verify(service, times(1)).saveProduct(nullNameProduct);
    }

    @Test
    public void testAddProductWithZeroPrice() {
        Product zeroPrice = new Product(4, "Free Item", 10, 0.0);
        when(service.saveProduct(zeroPrice)).thenReturn(zeroPrice);

        Product result = controller.addProduct(zeroPrice);

        assertEquals(0.0, result.getPrice());
        verify(service, times(1)).saveProduct(zeroPrice);
    }

    @Test
    public void testAddProductWithNegativeQuantity() {
        Product negQuantity = new Product(5, "Test", -5, 50.0);
        when(service.saveProduct(negQuantity)).thenReturn(negQuantity);

        Product result = controller.addProduct(negQuantity);

        assertEquals(-5, result.getQuantity());
        verify(service, times(1)).saveProduct(negQuantity);
    }

    @Test
    public void testAddProductWithMinimalData() {
        Product minimal = new Product(0, "", 0, 0.0);
        when(service.saveProduct(minimal)).thenReturn(minimal);

        Product result = controller.addProduct(minimal);

        assertEquals("", result.getName());
        assertEquals(0, result.getId());
        verify(service, times(1)).saveProduct(minimal);
    }

    @Test
    public void testAddProducts() {
        when(service.saveProducts(anyList())).thenReturn(productList);

        List<Product> result = controller.addProducts(productList);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(service, times(1)).saveProducts(productList);
    }

    @Test
    public void testAddProductsWithEmptyList() {
        List<Product> emptyList = Collections.emptyList();
        when(service.saveProducts(emptyList)).thenReturn(emptyList);

        List<Product> result = controller.addProducts(emptyList);

        assertTrue(result.isEmpty());
        verify(service, times(1)).saveProducts(emptyList);
    }

    @Test
    public void testAddProductsWithSingleItem() {
        List<Product> singleList = Collections.singletonList(product);
        when(service.saveProducts(singleList)).thenReturn(singleList);

        List<Product> result = controller.addProducts(singleList);

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getName());
        verify(service, times(1)).saveProducts(singleList);
    }

    @Test
    public void testAddProductsWithLargeList() {
        List<Product> largeList = Arrays.asList(product, product2, product, product2, product);
        when(service.saveProducts(largeList)).thenReturn(largeList);

        List<Product> result = controller.addProducts(largeList);

        assertEquals(5, result.size());
        verify(service, times(1)).saveProducts(largeList);
    }

    @Test
    public void testFindAllProducts() {
        when(service.getProducts()).thenReturn(productList);

        List<Product> result = controller.findAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(service, times(1)).getProducts();
    }

    @Test
    public void testFindAllProductsWhenEmpty() {
        when(service.getProducts()).thenReturn(Collections.emptyList());

        List<Product> result = controller.findAllProducts();

        assertTrue(result.isEmpty());
        verify(service, times(1)).getProducts();
    }

    @Test
    public void testFindAllProductsWithSingleItem() {
        when(service.getProducts()).thenReturn(Collections.singletonList(product));

        List<Product> result = controller.findAllProducts();

        assertEquals(1, result.size());
        verify(service, times(1)).getProducts();
    }

    @Test
    public void testFindProductById() {
        when(service.getProductById(1)).thenReturn(product);

        Product result = controller.findProductById(1);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        assertEquals(1, result.getId());
        verify(service, times(1)).getProductById(1);
    }

    @Test
    public void testFindProductByIdNotFound() {
        when(service.getProductById(999)).thenReturn(null);

        Product result = controller.findProductById(999);

        assertNull(result);
        verify(service, times(1)).getProductById(999);
    }

    @Test
    public void testFindProductByIdWithZero() {
        when(service.getProductById(0)).thenReturn(null);

        Product result = controller.findProductById(0);

        assertNull(result);
        verify(service, times(1)).getProductById(0);
    }

    @Test
    public void testFindProductByIdWithNegative() {
        when(service.getProductById(-1)).thenReturn(null);

        Product result = controller.findProductById(-1);

        assertNull(result);
        verify(service, times(1)).getProductById(-1);
    }

    @Test
    public void testFindProductByIdWithMaxInt() {
        when(service.getProductById(Integer.MAX_VALUE)).thenReturn(null);

        Product result = controller.findProductById(Integer.MAX_VALUE);

        assertNull(result);
        verify(service, times(1)).getProductById(Integer.MAX_VALUE);
    }

    @Test
    public void testFindProductByName() {
        when(service.getProductByName("Laptop")).thenReturn(product);

        Product result = controller.findProductByName("Laptop");

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(service, times(1)).getProductByName("Laptop");
    }

    @Test
    public void testFindProductByNameNotFound() {
        when(service.getProductByName("NonExistent")).thenReturn(null);

        Product result = controller.findProductByName("NonExistent");

        assertNull(result);
        verify(service, times(1)).getProductByName("NonExistent");
    }

    @Test
    public void testFindProductByNameWithEmpty() {
        when(service.getProductByName("")).thenReturn(null);

        Product result = controller.findProductByName("");

        assertNull(result);
        verify(service, times(1)).getProductByName("");
    }

    @Test
    public void testFindProductByNameWithSpecialCharacters() {
        String specialName = "Product-@#$%";
        Product specialProduct = new Product(10, specialName, 5, 100.0);
        when(service.getProductByName(specialName)).thenReturn(specialProduct);

        Product result = controller.findProductByName(specialName);

        assertEquals(specialName, result.getName());
        verify(service, times(1)).getProductByName(specialName);
    }

    @Test
    public void testFindProductByNameCaseSensitive() {
        when(service.getProductByName("LAPTOP")).thenReturn(null);
        when(service.getProductByName("Laptop")).thenReturn(product);

        Product result1 = controller.findProductByName("LAPTOP");
        Product result2 = controller.findProductByName("Laptop");

        assertNull(result1);
        assertNotNull(result2);
        verify(service, times(1)).getProductByName("LAPTOP");
        verify(service, times(1)).getProductByName("Laptop");
    }

    @Test
    public void testUpdateProduct() {
        Product updatedProduct = new Product(1, "Updated Laptop", 20, 1099.99);
        when(service.updateProduct(any(Product.class))).thenReturn(updatedProduct);

        Product result = controller.updateProduct(updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Laptop", result.getName());
        assertEquals(20, result.getQuantity());
        assertEquals(1099.99, result.getPrice());
        verify(service, times(1)).updateProduct(updatedProduct);
    }

    @Test
    public void testUpdateProductNotFound() {
        Product nonExistent = new Product(999, "New", 10, 50.0);
        when(service.updateProduct(nonExistent)).thenReturn(null);

        Product result = controller.updateProduct(nonExistent);

        assertNull(result);
        verify(service, times(1)).updateProduct(nonExistent);
    }

    @Test
    public void testUpdateProductWithNullName() {
        Product nullName = new Product(1, null, 15, 500.0);
        when(service.updateProduct(nullName)).thenReturn(nullName);

        Product result = controller.updateProduct(nullName);

        assertNull(result.getName());
        verify(service, times(1)).updateProduct(nullName);
    }

    @Test
    public void testUpdateProductWithZeroPrice() {
        Product zeroPrice = new Product(1, "Free", 10, 0.0);
        when(service.updateProduct(zeroPrice)).thenReturn(zeroPrice);

        Product result = controller.updateProduct(zeroPrice);

        assertEquals(0.0, result.getPrice());
        verify(service, times(1)).updateProduct(zeroPrice);
    }

    @Test
    public void testUpdateProductWithNegativeQuantity() {
        Product negQuantity = new Product(1, "Product", -10, 100.0);
        when(service.updateProduct(negQuantity)).thenReturn(negQuantity);

        Product result = controller.updateProduct(negQuantity);

        assertEquals(-10, result.getQuantity());
        verify(service, times(1)).updateProduct(negQuantity);
    }

    @Test
    public void testDeleteProduct() {
        String expectedMessage = "product removed !! 1";
        when(service.deleteProduct(1)).thenReturn(expectedMessage);

        String result = controller.deleteProduct(1);

        assertNotNull(result);
        assertEquals(expectedMessage, result);
        verify(service, times(1)).deleteProduct(1);
    }

    @Test
    public void testDeleteProductWithZeroId() {
        String expectedMessage = "product removed !! 0";
        when(service.deleteProduct(0)).thenReturn(expectedMessage);

        String result = controller.deleteProduct(0);

        assertTrue(result.contains("0"));
        verify(service, times(1)).deleteProduct(0);
    }

    @Test
    public void testDeleteProductWithNegativeId() {
        String expectedMessage = "product removed !! -1";
        when(service.deleteProduct(-1)).thenReturn(expectedMessage);

        String result = controller.deleteProduct(-1);

        assertTrue(result.contains("-1"));
        verify(service, times(1)).deleteProduct(-1);
    }

    @Test
    public void testDeleteProductWithMaxInt() {
        String expectedMessage = "product removed !! " + Integer.MAX_VALUE;
        when(service.deleteProduct(Integer.MAX_VALUE)).thenReturn(expectedMessage);

        String result = controller.deleteProduct(Integer.MAX_VALUE);

        assertTrue(result.contains(String.valueOf(Integer.MAX_VALUE)));
        verify(service, times(1)).deleteProduct(Integer.MAX_VALUE);
    }

    @Test
    public void testMultipleOperationsSequence() {
        when(service.saveProduct(product)).thenReturn(product);
        when(service.getProductById(1)).thenReturn(product);
        when(service.updateProduct(any(Product.class))).thenReturn(product);
        when(service.deleteProduct(1)).thenReturn("product removed !! 1");

        controller.addProduct(product);
        controller.findProductById(1);
        controller.updateProduct(product);
        controller.deleteProduct(1);

        verify(service, times(1)).saveProduct(product);
        verify(service, times(1)).getProductById(1);
        verify(service, times(1)).updateProduct(product);
        verify(service, times(1)).deleteProduct(1);
    }
}
