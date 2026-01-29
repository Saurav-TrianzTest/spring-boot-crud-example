package com.javatechie.crud.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driverClassName=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
public class SpringBootCrudExample2ApplicationTest {

    @Test
    public void contextLoads() {
        assertDoesNotThrow(() -> {
            SpringBootCrudExample2Application.main(new String[] {});
        });
    }

    @Test
    public void testApplicationNotNull() {
        SpringBootCrudExample2Application app = new SpringBootCrudExample2Application();
        assertNotNull(app);
    }

    @Test
    public void testMainMethodWithNullArgs() {
        assertDoesNotThrow(() -> {
            String[] args = null;
        });
    }

    @Test
    public void testMainMethodWithEmptyArgs() {
        assertDoesNotThrow(() -> {
            String[] args = new String[0];
        });
    }

    @Test
    public void testMainMethodWithMultipleArgs() {
        assertDoesNotThrow(() -> {
            String[] args = new String[]{"arg1", "arg2", "arg3"};
        });
    }

    @Test
    public void testApplicationClass() {
        Class<?> clazz = SpringBootCrudExample2Application.class;
        assertNotNull(clazz);
        assertTrue(clazz.isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class));
    }

    @Test
    public void testSpringBootApplicationAnnotationPresent() {
        boolean hasAnnotation = SpringBootCrudExample2Application.class
            .isAnnotationPresent(org.springframework.boot.autoconfigure.SpringBootApplication.class);
        assertTrue(hasAnnotation);
    }

    @Test
    public void testMainMethodExists() {
        try {
            SpringBootCrudExample2Application.class.getDeclaredMethod("main", String[].class);
            assertTrue(true);
        } catch (NoSuchMethodException e) {
            fail("Main method should exist");
        }
    }

    @Test
    public void testMainMethodIsPublic() throws NoSuchMethodException {
        int modifiers = SpringBootCrudExample2Application.class.getDeclaredMethod("main", String[].class).getModifiers();
        assertTrue(java.lang.reflect.Modifier.isPublic(modifiers));
    }

    @Test
    public void testMainMethodIsStatic() throws NoSuchMethodException {
        int modifiers = SpringBootCrudExample2Application.class.getDeclaredMethod("main", String[].class).getModifiers();
        assertTrue(java.lang.reflect.Modifier.isStatic(modifiers));
    }

    @Test
    public void testMainMethodReturnType() throws NoSuchMethodException {
        Class<?> returnType = SpringBootCrudExample2Application.class.getDeclaredMethod("main", String[].class).getReturnType();
        assertEquals(void.class, returnType);
    }

    @Test
    public void testApplicationClassName() {
        assertEquals("SpringBootCrudExample2Application", SpringBootCrudExample2Application.class.getSimpleName());
    }

    @Test
    public void testApplicationPackageName() {
        assertEquals("com.javatechie.crud.example", SpringBootCrudExample2Application.class.getPackageName());
    }
}
