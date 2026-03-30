package com.csc483.assignment1.search;

/**
 * Immutable product record used in the TechMart search tasks.
 */
public final class Product {
    private final int productId;
    private final String productName;
    private final String category;
    private final double price;
    private final int stockQuantity;

    public Product(int productId, String productName, String category, double price, int stockQuantity) {
        if (productName == null || category == null) {
            throw new IllegalArgumentException("productName and category must be non-null");
        }
        this.productId = productId;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + productId +
                ", name='" + productName + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", stock=" + stockQuantity +
                '}';
    }
}
