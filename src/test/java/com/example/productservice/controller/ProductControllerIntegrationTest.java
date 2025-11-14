package com.example.productservice.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Integration tests for ProductController using MockMvc and H2 database.
 */
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Product Controller Integration Tests")
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Product testProduct1;
    private Product testProduct2;

    /**
     * Setup method to initialize test data before each test.
     */
    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
        testProduct1 = new Product("Laptop", "Electronics", 999.99, 10);
        testProduct2 = new Product("Mouse", "Electronics", 29.99, 50);
    }

    /**
     * Tests for GET /api/products endpoint.
     */
    @Nested
    @DisplayName("GET /api/products Tests")
    class GetAllProductsTests {

        @Test
        @DisplayName("Should return all products")
        void testGetAllProducts() throws Exception {
            // Arrange
            productRepository.save(testProduct1);
            productRepository.save(testProduct2);

            // Act & Assert
            mockMvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].name", is("Laptop")))
                    .andExpect(jsonPath("$[0].price", is(999.99))).andExpect(jsonPath("$[1].name", is("Mouse")))
                    .andExpect(jsonPath("$[1].price", is(29.99)));
        }

        @Test
        @DisplayName("Should return empty list when no products exist")
        void testGetAllProductsEmpty() throws Exception {
            // Act & Assert
            mockMvc.perform(get("/api/products").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(0)));
        }
    }

    /**
     * Tests for GET /api/products/{id} endpoint.
     */
    @Nested
    @DisplayName("GET /api/products/{id} Tests")
    class GetProductByIdTests {

        @Test
        @DisplayName("Should return product when ID exists")
        void testGetProductByIdExists() throws Exception {
            // Arrange
            Product savedProduct = productRepository.save(testProduct1);

            // Act & Assert
            mockMvc.perform(get("/api/products/{id}", savedProduct.getId()).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andExpect(jsonPath("$.name", is("Laptop")))
                    .andExpect(jsonPath("$.category", is("Electronics"))).andExpect(jsonPath("$.price", is(999.99)))
                    .andExpect(jsonPath("$.stock", is(10)));
        }

        @Test
        @DisplayName("Should return 404 when product ID does not exist")
        void testGetProductByIdNotExists() throws Exception {
            // Act & Assert
            mockMvc.perform(get("/api/products/{id}", 999L).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    /**
     * Tests for POST /api/products endpoint.
     */
    @Nested
    @DisplayName("POST /api/products Tests")
    class CreateProductTests {

        @Test
        @DisplayName("Should create new product successfully")
        void testCreateProduct() throws Exception {
            // Arrange
            Product newProduct = new Product("Keyboard", "Electronics", 79.99, 25);
            String productJson = objectMapper.writeValueAsString(newProduct);

            // Act & Assert
            mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productJson))
                    .andExpect(status().isCreated()).andExpect(jsonPath("$.name", is("Keyboard")))
                    .andExpect(jsonPath("$.category", is("Electronics"))).andExpect(jsonPath("$.price", is(79.99)))
                    .andExpect(jsonPath("$.stock", is(25)));
        }

        @Test
        @DisplayName("Should return 400 when product data is invalid")
        void testCreateProductInvalid() throws Exception {
            // Arrange - Product with null name (invalid)
            Product invalidProduct = new Product(null, "Electronics", 79.99, 25);
            String productJson = objectMapper.writeValueAsString(invalidProduct);

            // Act & Assert
            mockMvc.perform(post("/api/products").contentType(MediaType.APPLICATION_JSON).content(productJson))
                    .andExpect(status().isBadRequest());
        }
    }

    /**
     * Tests for PUT /api/products/{id} endpoint.
     */
    @Nested
    @DisplayName("PUT /api/products/{id} Tests")
    class UpdateProductTests {

        @Test
        @DisplayName("Should update product when ID exists")
        void testUpdateProductExists() throws Exception {
            // Arrange
            Product savedProduct = productRepository.save(testProduct1);
            Product updatedProduct = new Product("Gaming Laptop", "Electronics", 1499.99, 5);
            String productJson = objectMapper.writeValueAsString(updatedProduct);

            // Act & Assert
            mockMvc.perform(put("/api/products/{id}", savedProduct.getId()).contentType(MediaType.APPLICATION_JSON)
                    .content(productJson)).andExpect(status().isOk()).andExpect(jsonPath("$.name", is("Gaming Laptop")))
                    .andExpect(jsonPath("$.price", is(1499.99))).andExpect(jsonPath("$.stock", is(5)));
        }

        @Test
        @DisplayName("Should return 404 when updating non-existent product")
        void testUpdateProductNotExists() throws Exception {
            // Arrange
            Product updatedProduct = new Product("Non-existent", "Electronics", 0.0, 0);
            String productJson = objectMapper.writeValueAsString(updatedProduct);

            // Act & Assert
            mockMvc.perform(
                    put("/api/products/{id}", 999L).contentType(MediaType.APPLICATION_JSON).content(productJson))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 400 when update data is invalid")
        void testUpdateProductInvalid() throws Exception {
            // Arrange
            Product savedProduct = productRepository.save(testProduct1);
            Product invalidProduct = new Product(null, "Electronics", 79.99, 25);
            String productJson = objectMapper.writeValueAsString(invalidProduct);

            // Act & Assert
            mockMvc.perform(put("/api/products/{id}", savedProduct.getId()).contentType(MediaType.APPLICATION_JSON)
                    .content(productJson)).andExpect(status().isBadRequest());
        }
    }

    /**
     * Tests for DELETE /api/products/{id} endpoint.
     */
    @Nested
    @DisplayName("DELETE /api/products/{id} Tests")
    class DeleteProductTests {

        @Test
        @DisplayName("Should delete product when ID exists")
        void testDeleteProductExists() throws Exception {
            // Arrange
            Product savedProduct = productRepository.save(testProduct1);

            // Act & Assert
            mockMvc.perform(delete("/api/products/{id}", savedProduct.getId()).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());

            // Verify deletion
            mockMvc.perform(get("/api/products/{id}", savedProduct.getId()).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 404 when deleting non-existent product")
        void testDeleteProductNotExists() throws Exception {
            // Act & Assert
            mockMvc.perform(delete("/api/products/{id}", 999L).contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    /**
     * Tests for GET /api/products/search endpoint.
     */
    @Nested
    @DisplayName("GET /api/products/search Tests")
    class SearchProductsTests {

        @Test
        @DisplayName("Should return products matching name pattern")
        void testSearchProductsByName() throws Exception {
            // Arrange
            productRepository.save(new Product("Laptop", "Electronics", 999.99, 10));
            productRepository.save(new Product("Gaming Mouse", "Electronics", 49.99, 20));
            productRepository.save(new Product("Keyboard", "Electronics", 79.99, 15));

            // Act & Assert
            mockMvc.perform(get("/api/products/search").param("name", "Laptop").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name", is("Laptop"))).andExpect(jsonPath("$[0].price", is(999.99)));
        }

        @Test
        @DisplayName("Should return products matching category")
        void testSearchProductsByCategory() throws Exception {
            // Arrange
            productRepository.save(new Product("Laptop", "Electronics", 999.99, 10));
            productRepository.save(new Product("Mouse", "Electronics", 29.99, 50));
            productRepository.save(new Product("Book", "Books", 19.99, 100));

            // Act & Assert
            mockMvc.perform(get("/api/products/search").param("category", "Electronics")
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].category", is("Electronics")))
                    .andExpect(jsonPath("$[1].category", is("Electronics")));
        }

        @Test
        @DisplayName("Should return products within price range")
        void testSearchProductsByPriceRange() throws Exception {
            // Arrange
            productRepository.save(new Product("Laptop", "Electronics", 999.99, 10));
            productRepository.save(new Product("Mouse", "Electronics", 29.99, 50));
            productRepository.save(new Product("Keyboard", "Electronics", 79.99, 15));

            // Act & Assert
            mockMvc.perform(get("/api/products/search").param("minPrice", "0").param("maxPrice", "50")
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].name", is("Mouse")))
                    .andExpect(jsonPath("$[0].price", is(29.99)));
        }

        @Test
        @DisplayName("Should return products matching all criteria")
        void testSearchProductsWithAllCriteria() throws Exception {
            // Arrange
            productRepository.save(new Product("Laptop", "Electronics", 999.99, 10));
            productRepository.save(new Product("Gaming Mouse", "Electronics", 49.99, 20));
            productRepository.save(new Product("Keyboard", "Electronics", 79.99, 15));

            // Act & Assert
            mockMvc.perform(get("/api/products/search").param("name", "Mouse").param("category", "Electronics")
                    .param("minPrice", "0").param("maxPrice", "50").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
                    .andExpect(jsonPath("$[0].name", is("Gaming Mouse"))).andExpect(jsonPath("$[0].price", is(49.99)));
        }

        @Test
        @DisplayName("Should return all products when no filters provided")
        void testSearchProductsWithNoFilters() throws Exception {
            // Arrange
            productRepository.save(testProduct1);
            productRepository.save(testProduct2);

            // Act & Assert
            mockMvc.perform(get("/api/products/search").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
        }

        @Test
        @DisplayName("Should return empty list when no products match criteria")
        void testSearchProductsNoMatch() throws Exception {
            // Arrange
            productRepository.save(testProduct1);
            productRepository.save(testProduct2);

            // Act & Assert
            mockMvc.perform(
                    get("/api/products/search").param("name", "NonExistent").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(0)));
        }

        @Test
        @DisplayName("Should return products matching name and category")
        void testSearchProductsByNameAndCategory() throws Exception {
            // Arrange
            productRepository.save(new Product("Laptop", "Electronics", 999.99, 10));
            productRepository.save(new Product("Laptop Bag", "Accessories", 49.99, 20));
            productRepository.save(new Product("Gaming Mouse", "Electronics", 29.99, 50));

            // Act & Assert
            mockMvc.perform(get("/api/products/search").param("name", "Laptop").param("category", "Electronics")
                    .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].name", is("Laptop")))
                    .andExpect(jsonPath("$[0].category", is("Electronics")));
        }
    }
}
