package com.example.productservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.productservice.entity.Product;

/**
 * Repository interface for Product entity. Extends JpaRepository to provide
 * CRUD operations.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Finds products by category.
     *
     * @param pCategory
     *            the category to search for
     * @return list of products in the specified category
     */
    List<Product> findByCategory(String pCategory);

    /**
     * Finds products by name containing the given string (case-insensitive).
     *
     * @param pName
     *            the name pattern to search for
     * @return list of products matching the name pattern
     */
    List<Product> findByNameContainingIgnoreCase(String pName);

    /**
     * Finds products within a price range.
     *
     * @param pMinPrice
     *            the minimum price (inclusive)
     * @param pMaxPrice
     *            the maximum price (inclusive)
     * @return list of products within the price range
     */
    @Query("SELECT p FROM Product p WHERE p.price >= :minPrice AND p.price <= :maxPrice")
    List<Product> findByPriceBetween(@Param("minPrice") Double pMinPrice, @Param("maxPrice") Double pMaxPrice);

    /**
     * Finds products matching multiple criteria: name pattern, category, and price
     * range.
     *
     * @param pName
     *            the name pattern to search for (can be null)
     * @param pCategory
     *            the category to filter by (can be null)
     * @param pMinPrice
     *            the minimum price (inclusive, can be null)
     * @param pMaxPrice
     *            the maximum price (inclusive, can be null)
     * @return list of products matching all specified criteria
     */
    @Query("SELECT p FROM Product p WHERE " + "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) "
            + "AND (:category IS NULL OR p.category = :category) " + "AND (:minPrice IS NULL OR p.price >= :minPrice) "
            + "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> searchProducts(@Param("name") String pName, @Param("category") String pCategory,
            @Param("minPrice") Double pMinPrice, @Param("maxPrice") Double pMaxPrice);
}
