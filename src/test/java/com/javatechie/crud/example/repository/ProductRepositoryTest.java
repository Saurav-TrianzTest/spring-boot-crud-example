package com.javatechie.crud.example.repository;

import com.javatechie.crud.example.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@org.springframework.test.context.TestPropertySource(properties = {
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repository;

    private Product product;
    private Product product2;

    @BeforeEach
    public void setUp() {
        product = new Product();
        product.setName("Laptop");
        product.setQuantity(10);
        product.setPrice(999.99);

        product2 = new Product();
        product2.setName("Mouse");
        product2.setQuantity(50);
        product2.setPrice(25.50);
    }

    @Test
    public void testRepositoryIsNotNull() {
        assertNotNull(repository);
    }

    @Test
    public void testSaveProduct() {
        Product saved = repository.save(product);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Laptop", saved.getName());
        assertEquals(10, saved.getQuantity());
        assertEquals(999.99, saved.getPrice());
    }

    @Test
    public void testSaveProductWithNullName() {
        product.setName(null);
        Product saved = repository.save(product);

        assertNotNull(saved);
        assertNull(saved.getName());
    }

    @Test
    public void testSaveProductWithEmptyName() {
        product.setName("");
        Product saved = repository.save(product);

        assertEquals("", saved.getName());
    }

    @Test
    public void testSaveProductWithZeroQuantity() {
        product.setQuantity(0);
        Product saved = repository.save(product);

        assertEquals(0, saved.getQuantity());
    }

    @Test
    public void testSaveProductWithNegativeQuantity() {
        product.setQuantity(-5);
        Product saved = repository.save(product);

        assertEquals(-5, saved.getQuantity());
    }

    @Test
    public void testSaveProductWithZeroPrice() {
        product.setPrice(0.0);
        Product saved = repository.save(product);

        assertEquals(0.0, saved.getPrice());
    }

    @Test
    public void testSaveProductWithNegativePrice() {
        product.setPrice(-10.0);
        Product saved = repository.save(product);

        assertEquals(-10.0, saved.getPrice());
    }

    @Test
    public void testSaveMultipleProducts() {
        Product saved1 = repository.save(product);
        Product saved2 = repository.save(product2);

        assertNotNull(saved1);
        assertNotNull(saved2);
        assertNotEquals(saved1.getId(), saved2.getId());
    }

    @Test
    public void testFindById() {
        Product saved = repository.save(product);
        int id = saved.getId();

        Optional<Product> found = repository.findById(id);

        assertTrue(found.isPresent());
        assertEquals("Laptop", found.get().getName());
    }

    @Test
    public void testFindByIdNotFound() {
        Optional<Product> found = repository.findById(999);

        assertFalse(found.isPresent());
    }

    @Test
    public void testFindByIdWithZero() {
        Optional<Product> found = repository.findById(0);

        assertFalse(found.isPresent());
    }

    @Test
    public void testFindByIdWithNegative() {
        Optional<Product> found = repository.findById(-1);

        assertFalse(found.isPresent());
    }

    @Test
    public void testFindAll() {
        repository.save(product);
        repository.save(product2);

        List<Product> products = repository.findAll();

        assertNotNull(products);
        assertEquals(2, products.size());
    }

    @Test
    public void testFindAllWhenEmpty() {
        List<Product> products = repository.findAll();

        assertTrue(products.isEmpty());
    }

    @Test
    public void testFindAllWithSingleProduct() {
        repository.save(product);

        List<Product> products = repository.findAll();

        assertEquals(1, products.size());
        assertEquals("Laptop", products.get(0).getName());
    }

    @Test
    public void testFindByName() {
        repository.save(product);

        Product found = repository.findByName("Laptop");

        assertNotNull(found);
        assertEquals("Laptop", found.getName());
        assertEquals(10, found.getQuantity());
    }

    @Test
    public void testFindByNameNotFound() {
        repository.save(product);

        Product found = repository.findByName("NonExistent");

        assertNull(found);
    }

    @Test
    public void testFindByNameWithNull() {
        Product nullName = new Product();
        nullName.setName(null);
        nullName.setQuantity(5);
        nullName.setPrice(50.0);
        repository.save(nullName);

        Product found = repository.findByName(null);

        assertNotNull(found);
        assertNull(found.getName());
    }

    @Test
    public void testFindByNameWithEmpty() {
        product.setName("");
        repository.save(product);

        Product found = repository.findByName("");

        assertNotNull(found);
        assertEquals("", found.getName());
    }

    @Test
    public void testFindByNameCaseSensitive() {
        repository.save(product);

        Product found1 = repository.findByName("Laptop");
        Product found2 = repository.findByName("LAPTOP");
        Product found3 = repository.findByName("laptop");

        assertNotNull(found1);
        assertNull(found2);
        assertNull(found3);
    }

    @Test
    public void testFindByNameWithSpecialCharacters() {
        product.setName("Product-@#$%");
        repository.save(product);

        Product found = repository.findByName("Product-@#$%");

        assertNotNull(found);
        assertEquals("Product-@#$%", found.getName());
    }

    @Test
    public void testFindByNameWhenMultipleProductsExist() {
        repository.save(product);
        repository.save(product2);

        Product found = repository.findByName("Mouse");

        assertNotNull(found);
        assertEquals("Mouse", found.getName());
        assertEquals(25.50, found.getPrice());
    }

    @Test
    public void testUpdateProduct() {
        Product saved = repository.save(product);
        int id = saved.getId();

        saved.setName("Updated Laptop");
        saved.setQuantity(20);
        saved.setPrice(1099.99);
        Product updated = repository.save(saved);

        assertEquals(id, updated.getId());
        assertEquals("Updated Laptop", updated.getName());
        assertEquals(20, updated.getQuantity());
        assertEquals(1099.99, updated.getPrice());
    }

    @Test
    public void testUpdateProductName() {
        Product saved = repository.save(product);

        saved.setName("New Name");
        Product updated = repository.save(saved);

        assertEquals("New Name", updated.getName());
        assertEquals(10, updated.getQuantity());
        assertEquals(999.99, updated.getPrice());
    }

    @Test
    public void testUpdateProductQuantity() {
        Product saved = repository.save(product);

        saved.setQuantity(100);
        Product updated = repository.save(saved);

        assertEquals(100, updated.getQuantity());
    }

    @Test
    public void testUpdateProductPrice() {
        Product saved = repository.save(product);

        saved.setPrice(1500.00);
        Product updated = repository.save(saved);

        assertEquals(1500.00, updated.getPrice());
    }

    @Test
    public void testDeleteProduct() {
        Product saved = repository.save(product);
        int id = saved.getId();

        repository.deleteById(id);

        Optional<Product> found = repository.findById(id);
        assertFalse(found.isPresent());
    }

    @Test
    public void testDeleteProductNotAffectingOthers() {
        Product saved1 = repository.save(product);
        Product saved2 = repository.save(product2);

        repository.deleteById(saved1.getId());

        Optional<Product> found = repository.findById(saved2.getId());
        assertTrue(found.isPresent());
        assertEquals("Mouse", found.get().getName());
    }

    @Test
    public void testDeleteAllProducts() {
        repository.save(product);
        repository.save(product2);

        repository.deleteAll();

        List<Product> products = repository.findAll();
        assertTrue(products.isEmpty());
    }

    @Test
    public void testCount() {
        repository.save(product);
        repository.save(product2);

        long count = repository.count();

        assertEquals(2, count);
    }

    @Test
    public void testCountWhenEmpty() {
        long count = repository.count();

        assertEquals(0, count);
    }

    @Test
    public void testExistsById() {
        Product saved = repository.save(product);

        boolean exists = repository.existsById(saved.getId());

        assertTrue(exists);
    }

    @Test
    public void testExistsByIdNotFound() {
        boolean exists = repository.existsById(999);

        assertFalse(exists);
    }

    @Test
    public void testSaveAndFlush() {
        Product saved = repository.saveAndFlush(product);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Laptop", saved.getName());
    }

    @Test
    public void testProductPersistence() {
        Product saved = repository.save(product);
        entityManager.flush();
        entityManager.clear();

        Optional<Product> found = repository.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals(saved.getId(), found.get().getId());
        assertEquals("Laptop", found.get().getName());
    }
}
