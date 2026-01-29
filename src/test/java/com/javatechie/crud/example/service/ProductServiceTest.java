package com.javatechie.crud.example.service;

import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

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
        ProductService testService = new ProductService(repository);
        assertNotNull(testService);
    }

    @Test
    public void testSaveProduct() {
        when(repository.save(any(Product.class))).thenReturn(product);

        Product savedProduct = service.saveProduct(product);

        assertNotNull(savedProduct);
        assertEquals("Laptop", savedProduct.getName());
        assertEquals(1, savedProduct.getId());
        verify(repository, times(1)).save(product);
    }

    @Test
    public void testSaveProductWithNull() {
        when(repository.save(null)).thenReturn(null);

        Product savedProduct = service.saveProduct(null);

        assertNull(savedProduct);
        verify(repository, times(1)).save(null);
    }

    @Test
    public void testSaveProductWithZeroPrice() {
        Product zeroPrice = new Product(3, "Free Item", 5, 0.0);
        when(repository.save(zeroPrice)).thenReturn(zeroPrice);

        Product savedProduct = service.saveProduct(zeroPrice);

        assertEquals(0.0, savedProduct.getPrice());
        verify(repository, times(1)).save(zeroPrice);
    }

    @Test
    public void testSaveProductWithNegativeQuantity() {
        Product negQuantity = new Product(4, "Test", -5, 10.0);
        when(repository.save(negQuantity)).thenReturn(negQuantity);

        Product savedProduct = service.saveProduct(negQuantity);

        assertEquals(-5, savedProduct.getQuantity());
        verify(repository, times(1)).save(negQuantity);
    }

    @Test
    public void testSaveProducts() {
        when(repository.saveAll(anyList())).thenReturn(productList);

        List<Product> savedProducts = service.saveProducts(productList);

        assertNotNull(savedProducts);
        assertEquals(2, savedProducts.size());
        verify(repository, times(1)).saveAll(productList);
    }

    @Test
    public void testSaveProductsWithEmptyList() {
        List<Product> emptyList = Collections.emptyList();
        when(repository.saveAll(emptyList)).thenReturn(emptyList);

        List<Product> savedProducts = service.saveProducts(emptyList);

        assertTrue(savedProducts.isEmpty());
        verify(repository, times(1)).saveAll(emptyList);
    }

    @Test
    public void testSaveProductsWithSingleItem() {
        List<Product> singleList = Collections.singletonList(product);
        when(repository.saveAll(singleList)).thenReturn(singleList);

        List<Product> savedProducts = service.saveProducts(singleList);

        assertEquals(1, savedProducts.size());
        verify(repository, times(1)).saveAll(singleList);
    }

    @Test
    public void testGetProducts() {
        when(repository.findAll()).thenReturn(productList);

        List<Product> products = service.getProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetProductsWhenEmpty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Product> products = service.getProducts();

        assertTrue(products.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetProductById() {
        when(repository.findById(1)).thenReturn(Optional.of(product));

        Product foundProduct = service.getProductById(1);

        assertNotNull(foundProduct);
        assertEquals("Laptop", foundProduct.getName());
        assertEquals(1, foundProduct.getId());
        verify(repository, times(1)).findById(1);
    }

    @Test
    public void testGetProductByIdNotFound() {
        when(repository.findById(999)).thenReturn(Optional.empty());

        Product foundProduct = service.getProductById(999);

        assertNull(foundProduct);
        verify(repository, times(1)).findById(999);
    }

    @Test
    public void testGetProductByIdWithZero() {
        when(repository.findById(0)).thenReturn(Optional.empty());

        Product foundProduct = service.getProductById(0);

        assertNull(foundProduct);
        verify(repository, times(1)).findById(0);
    }

    @Test
    public void testGetProductByIdWithNegative() {
        when(repository.findById(-1)).thenReturn(Optional.empty());

        Product foundProduct = service.getProductById(-1);

        assertNull(foundProduct);
        verify(repository, times(1)).findById(-1);
    }

    @Test
    public void testGetProductByName() {
        when(repository.findByName("Laptop")).thenReturn(product);

        Product foundProduct = service.getProductByName("Laptop");

        assertNotNull(foundProduct);
        assertEquals("Laptop", foundProduct.getName());
        verify(repository, times(1)).findByName("Laptop");
    }

    @Test
    public void testGetProductByNameNotFound() {
        when(repository.findByName("NonExistent")).thenReturn(null);

        Product foundProduct = service.getProductByName("NonExistent");

        assertNull(foundProduct);
        verify(repository, times(1)).findByName("NonExistent");
    }

    @Test
    public void testGetProductByNameWithNull() {
        when(repository.findByName(null)).thenReturn(null);

        Product foundProduct = service.getProductByName(null);

        assertNull(foundProduct);
        verify(repository, times(1)).findByName(null);
    }

    @Test
    public void testGetProductByNameWithEmpty() {
        when(repository.findByName("")).thenReturn(null);

        Product foundProduct = service.getProductByName("");

        assertNull(foundProduct);
        verify(repository, times(1)).findByName("");
    }

    @Test
    public void testDeleteProduct() {
        doNothing().when(repository).deleteById(1);

        String result = service.deleteProduct(1);

        assertNotNull(result);
        assertTrue(result.contains("product removed"));
        assertTrue(result.contains("1"));
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteProductWithZeroId() {
        doNothing().when(repository).deleteById(0);

        String result = service.deleteProduct(0);

        assertTrue(result.contains("0"));
        verify(repository, times(1)).deleteById(0);
    }

    @Test
    public void testDeleteProductWithNegativeId() {
        doNothing().when(repository).deleteById(-1);

        String result = service.deleteProduct(-1);

        assertTrue(result.contains("-1"));
        verify(repository, times(1)).deleteById(-1);
    }

    @Test
    public void testUpdateProduct() {
        Product updatedProduct = new Product(1, "Updated Laptop", 20, 1099.99);
        when(repository.findById(1)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = service.updateProduct(updatedProduct);

        assertNotNull(result);
        assertEquals("Updated Laptop", result.getName());
        assertEquals(20, result.getQuantity());
        assertEquals(1099.99, result.getPrice());
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    public void testUpdateProductNotFound() {
        Product updatedProduct = new Product(999, "New Product", 10, 50.0);
        when(repository.findById(999)).thenReturn(Optional.empty());

        Product result = service.updateProduct(updatedProduct);

        assertNull(result);
        verify(repository, times(1)).findById(999);
        verify(repository, never()).save(any(Product.class));
    }

    @Test
    public void testUpdateProductWithNullName() {
        Product updatedProduct = new Product(1, null, 15, 500.0);
        when(repository.findById(1)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = service.updateProduct(updatedProduct);

        assertNotNull(result);
        assertNull(result.getName());
        verify(repository, times(1)).findById(1);
    }

    @Test
    public void testUpdateProductWithZeroPrice() {
        Product updatedProduct = new Product(1, "Free Item", 10, 0.0);
        when(repository.findById(1)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = service.updateProduct(updatedProduct);

        assertEquals(0.0, result.getPrice());
        verify(repository, times(1)).findById(1);
    }

    @Test
    public void testUpdateProductWithNegativeQuantity() {
        Product updatedProduct = new Product(1, "Product", -5, 100.0);
        when(repository.findById(1)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = service.updateProduct(updatedProduct);

        assertEquals(-5, result.getQuantity());
        verify(repository, times(1)).findById(1);
    }

    @Test
    public void testUpdateProductPartialUpdate() {
        Product updatedProduct = new Product(1, "Partially Updated", 10, 999.99);
        when(repository.findById(1)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = service.updateProduct(updatedProduct);

        assertNotNull(result);
        assertEquals("Partially Updated", result.getName());
        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).save(any(Product.class));
    }
}
