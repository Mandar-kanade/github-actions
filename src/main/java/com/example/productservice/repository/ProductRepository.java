package com.example.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.productservice.entity.Product;

/**
 * Repository interface for Product entity. Extends JpaRepository to provide
 * CRUD operations.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
