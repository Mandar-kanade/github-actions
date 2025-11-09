package com.example.productservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;

/**
 * Unit tests for ProductService using JUnit 5 and Mockito.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Product Service Tests")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct1;
    private Product testProduct2;

    /**
     * Setup method to initialize test data.
     */
    @BeforeEach
    void setUp() {
        testProduct1 = new Product(1L, "Laptop", "Electronics", 999.99, 10);
        testProduct2 = new Product(2L, "Mouse", "Electronics", 29.99, 50);
    }

    /**
     * Tests for getAllProducts method.
     */
    @Nested
    @DisplayName("Get All Products Tests")
    class GetAllProductsTests {

        @Test
        @DisplayName("Should return all products")
        void testGetAllProducts() {
            // Arrange
            List<Product> expectedProducts = Arrays.asList(testProduct1, testProduct2);
            when(productRepository.findAll()).thenReturn(expectedProducts);

            // Act
            List<Product> actualProducts = productService.getAllProducts();

            // Assert
            assertEquals(2, actualProducts.size());
            assertEquals(expectedProducts, actualProducts);
            verify(productRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return empty list when no products exist")
        void testGetAllProductsEmpty() {
            // Arrange
            when(productRepository.findAll()).thenReturn(Arrays.asList());

            // Act
            List<Product> actualProducts = productService.getAllProducts();

            // Assert
            assertEquals(0, actualProducts.size());
            verify(productRepository, times(1)).findAll();
        }
    }

    /**
     * Tests for getProductById method.
     */
    @Nested
    @DisplayName("Get Product By ID Tests")
    class GetProductByIdTests {

        @Test
        @DisplayName("Should return product when ID exists")
        void testGetProductByIdExists() {
            // Arrange
            when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct1));

            // Act
            Optional<Product> result = productService.getProductById(1L);

            // Assert
            assertTrue(result.isPresent());
            assertEquals(testProduct1, result.get());
            verify(productRepository, times(1)).findById(1L);
        }

        @Test
        @DisplayName("Should return empty when ID does not exist")
        void testGetProductByIdNotExists() {
            // Arrange
            when(productRepository.findById(999L)).thenReturn(Optional.empty());

            // Act
            Optional<Product> result = productService.getProductById(999L);

            // Assert
            assertFalse(result.isPresent());
            verify(productRepository, times(1)).findById(999L);
        }
    }

    /**
     * Tests for addProduct method.
     */
    @Nested
    @DisplayName("Add Product Tests")
    class AddProductTests {

        @Test
        @DisplayName("Should add new product successfully")
        void testAddProduct() {
            // Arrange
            Product newProduct = new Product("Keyboard", "Electronics", 79.99, 25);
            Product savedProduct = new Product(3L, "Keyboard", "Electronics", 79.99, 25);
            when(productRepository.save(newProduct)).thenReturn(savedProduct);

            // Act
            Product result = productService.addProduct(newProduct);

            // Assert
            assertEquals(3L, result.getId());
            assertEquals("Keyboard", result.getName());
            verify(productRepository, times(1)).save(newProduct);
        }
    }

    /**
     * Tests for updateProduct method.
     */
    @Nested
    @DisplayName("Update Product Tests")
    class UpdateProductTests {

        @Test
        @DisplayName("Should update product when ID exists")
        void testUpdateProductExists() {
            // Arrange
            Product updatedProduct = new Product(1L, "Gaming Laptop", "Electronics", 1499.99, 5);
            when(productRepository.findById(1L)).thenReturn(Optional.of(testProduct1));
            when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

            // Act
            Optional<Product> result = productService.updateProduct(1L, updatedProduct);

            // Assert
            assertTrue(result.isPresent());
            assertEquals("Gaming Laptop", result.get().getName());
            assertEquals(1499.99, result.get().getPrice());
            verify(productRepository, times(1)).findById(1L);
            verify(productRepository, times(1)).save(any(Product.class));
        }

        @Test
        @DisplayName("Should return empty when updating non-existent product")
        void testUpdateProductNotExists() {
            // Arrange
            Product updatedProduct = new Product(999L, "Non-existent", "Electronics", 0.0, 0);
            when(productRepository.findById(999L)).thenReturn(Optional.empty());

            // Act
            Optional<Product> result = productService.updateProduct(999L, updatedProduct);

            // Assert
            assertFalse(result.isPresent());
            verify(productRepository, times(1)).findById(999L);
            verify(productRepository, never()).save(any(Product.class));
        }
    }

    /**
     * Tests for deleteProduct method.
     */
    @Nested
    @DisplayName("Delete Product Tests")
    class DeleteProductTests {

        @Test
        @DisplayName("Should delete product when ID exists")
        void testDeleteProductExists() {
            // Arrange
            when(productRepository.existsById(1L)).thenReturn(true);

            // Act
            boolean result = productService.deleteProduct(1L);

            // Assert
            assertTrue(result);
            verify(productRepository, times(1)).existsById(1L);
            verify(productRepository, times(1)).deleteById(1L);
        }

        @Test
        @DisplayName("Should return false when deleting non-existent product")
        void testDeleteProductNotExists() {
            // Arrange
            when(productRepository.existsById(999L)).thenReturn(false);

            // Act
            boolean result = productService.deleteProduct(999L);

            // Assert
            assertFalse(result);
            verify(productRepository, times(1)).existsById(999L);
            verify(productRepository, never()).deleteById(999L);
        }
    }
}
