package com.example.productservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Product entity representing a product in the database.
 */
@Entity
@Table(name = "products")
public class Product {

    // CHECKSTYLE:OFF - JPA entity fields use standard naming conventions
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Product category is required")
    private String category;

    @NotNull(message = "Product price is required")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    @NotNull(message = "Product stock is required")
    @Min(value = 0, message = "Stock must be greater than or equal to 0")
    private Integer stock;
    // CHECKSTYLE:ON

    /**
     * Default constructor.
     */
    public Product() {
    }

    /**
     * Constructor with all fields.
     *
     * @param pId
     *            the product ID
     * @param pName
     *            the product name
     * @param pCategory
     *            the product category
     * @param pPrice
     *            the product price
     * @param pStock
     *            the product stock
     */
    public Product(Long pId, String pName, String pCategory, Double pPrice, Integer pStock) {
        this.id = pId;
        this.name = pName;
        this.category = pCategory;
        this.price = pPrice;
        this.stock = pStock;
    }

    /**
     * Constructor without ID (for new products).
     *
     * @param pName
     *            the product name
     * @param pCategory
     *            the product category
     * @param pPrice
     *            the product price
     * @param pStock
     *            the product stock
     */
    public Product(String pName, String pCategory, Double pPrice, Integer pStock) {
        this.name = pName;
        this.category = pCategory;
        this.price = pPrice;
        this.stock = pStock;
    }

    /**
     * Gets the product ID.
     *
     * @return the product ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the product ID.
     *
     * @param pId
     *            the product ID to set
     */
    public void setId(Long pId) {
        this.id = pId;
    }

    /**
     * Gets the product name.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the product name.
     *
     * @param pName
     *            the product name to set
     */
    public void setName(String pName) {
        this.name = pName;
    }

    /**
     * Gets the product category.
     *
     * @return the product category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the product category.
     *
     * @param pCategory
     *            the product category to set
     */
    public void setCategory(String pCategory) {
        this.category = pCategory;
    }

    /**
     * Gets the product price.
     *
     * @return the product price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets the product price.
     *
     * @param pPrice
     *            the product price to set
     */
    public void setPrice(Double pPrice) {
        this.price = pPrice;
    }

    /**
     * Gets the product stock.
     *
     * @return the product stock
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * Sets the product stock.
     *
     * @param pStock
     *            the product stock to set
     */
    public void setStock(Integer pStock) {
        this.stock = pStock;
    }
}
