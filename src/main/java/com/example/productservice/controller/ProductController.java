package com.example.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.productservice.entity.Product;
import com.example.productservice.service.ProductService;

import jakarta.validation.Valid;

/**
 * REST Controller for Product operations. Exposes endpoints under
 * /api/products.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    // CHECKSTYLE:OFF - Spring managed bean fields use standard naming
    private final ProductService productService;
    // CHECKSTYLE:ON

    /**
     * Constructor with dependency injection.
     *
     * @param pProductService
     *            the product service
     */
    @Autowired
    public ProductController(ProductService pProductService) {
        this.productService = pProductService;
    }

    /**
     * GET endpoint to retrieve all products.
     *
     * @return list of all products
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * GET endpoint to retrieve a product by ID.
     *
     * @param pId
     *            the product ID
     * @return the product if found, 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long pId) {
        return productService.getProductById(pId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST endpoint to create a new product.
     *
     * @param pProduct
     *            the product to create
     * @return the created product with 201 status
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product pProduct) {
        Product savedProduct = productService.addProduct(pProduct);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    /**
     * PUT endpoint to update an existing product.
     *
     * @param pId
     *            the product ID to update
     * @param pProduct
     *            the product data to update
     * @return the updated product if found, 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long pId, @Valid @RequestBody Product pProduct) {
        return productService.updateProduct(pId, pProduct).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE endpoint to delete a product.
     *
     * @param pId
     *            the product ID to delete
     * @return 204 if deleted successfully, 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long pId) {
        if (productService.deleteProduct(pId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * GET endpoint to search products with multiple optional filters.
     *
     * @param pName
     *            optional name pattern to search for (case-insensitive)
     * @param pCategory
     *            optional category to filter by
     * @param pMinPrice
     *            optional minimum price (inclusive)
     * @param pMaxPrice
     *            optional maximum price (inclusive)
     * @return list of products matching the search criteria
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam(value = "name", required = false) String pName,
            @RequestParam(value = "category", required = false) String pCategory,
            @RequestParam(value = "minPrice", required = false) Double pMinPrice,
            @RequestParam(value = "maxPrice", required = false) Double pMaxPrice) {
        List<Product> products = productService.searchProducts(pName, pCategory, pMinPrice, pMaxPrice);
        return ResponseEntity.ok(products);
    }
}
