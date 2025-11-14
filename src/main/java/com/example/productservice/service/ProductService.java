package com.example.productservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.productservice.entity.Product;
import com.example.productservice.repository.ProductRepository;

/**
 * Service class for managing Product operations. Contains business logic for
 * CRUD operations.
 */
@Service
public class ProductService {

    // CHECKSTYLE:OFF - Spring managed bean fields use standard naming
    private final ProductRepository productRepository;
    // CHECKSTYLE:ON

    /**
     * Constructor with dependency injection.
     *
     * @param pProductRepository
     *            the product repository
     */
    @Autowired
    public ProductService(ProductRepository pProductRepository) {
        this.productRepository = pProductRepository;
    }

    /**
     * Retrieves all products from the database.
     *
     * @return list of all products
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param pId
     *            the product ID
     * @return an Optional containing the product if found
     */
    public Optional<Product> getProductById(Long pId) {
        return productRepository.findById(pId);
    }

    /**
     * Adds a new product to the database.
     *
     * @param pProduct
     *            the product to add
     * @return the saved product with generated ID
     */
    public Product addProduct(Product pProduct) {
        return productRepository.save(pProduct);
    }

    /**
     * Updates an existing product.
     *
     * @param pId
     *            the product ID to update
     * @param pProduct
     *            the product data to update
     * @return an Optional containing the updated product if found
     */
    public Optional<Product> updateProduct(Long pId, Product pProduct) {
        return productRepository.findById(pId).map(existingProduct -> {
            existingProduct.setName(pProduct.getName());
            existingProduct.setCategory(pProduct.getCategory());
            existingProduct.setPrice(pProduct.getPrice());
            existingProduct.setStock(pProduct.getStock());
            return productRepository.save(existingProduct);
        });
    }

    /**
     * Deletes a product by its ID.
     *
     * @param pId
     *            the product ID to delete
     * @return true if product was deleted, false if not found
     */
    public boolean deleteProduct(Long pId) {
        if (productRepository.existsById(pId)) {
            productRepository.deleteById(pId);
            return true;
        }
        return false;
    }

    /**
     * Searches products by name (case-insensitive partial match).
     *
     * @param pName
     *            the name pattern to search for
     * @return list of products matching the name pattern
     */
    public List<Product> searchProductsByName(String pName) {
        return productRepository.findByNameContainingIgnoreCase(pName);
    }

    /**
     * Finds products by category.
     *
     * @param pCategory
     *            the category to filter by
     * @return list of products in the specified category
     */
    public List<Product> getProductsByCategory(String pCategory) {
        return productRepository.findByCategory(pCategory);
    }

    /**
     * Finds products within a price range.
     *
     * @param pMinPrice
     *            the minimum price (inclusive)
     * @param pMaxPrice
     *            the maximum price (inclusive)
     * @return list of products within the price range
     */
    public List<Product> getProductsByPriceRange(Double pMinPrice, Double pMaxPrice) {
        return productRepository.findByPriceBetween(pMinPrice, pMaxPrice);
    }

    /**
     * Advanced search for products with multiple criteria. All parameters are
     * optional and can be combined.
     *
     * @param pName
     *            the name pattern to search for (optional)
     * @param pCategory
     *            the category to filter by (optional)
     * @param pMinPrice
     *            the minimum price (optional)
     * @param pMaxPrice
     *            the maximum price (optional)
     * @return list of products matching all specified criteria
     */
    public List<Product> searchProducts(String pName, String pCategory, Double pMinPrice, Double pMaxPrice) {
        return productRepository.searchProducts(pName, pCategory, pMinPrice, pMaxPrice);
    }
}
