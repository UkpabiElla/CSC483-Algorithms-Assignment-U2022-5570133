package com.csc483.assignment1.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductIndexTest {
    @Test
    void indexFindsByName() {
        Product[] inventorySnapshot = {
                new Product(1, "Alpha", "C", 10.0, 2),
                new Product(2, "Beta", "C", 11.0, 3)
        };

        ProductIndex index = ProductIndex.fromInventorySnapshot(inventorySnapshot);
        Product found = index.lookupByName("Alpha");

        assertNotNull(found);
        assertEquals(1, found.getProductId());
    }

    @Test
    void insertProductUpdatesIndex() {
        ProductIndex index = new ProductIndex();
        Product product = new Product(3, "Gamma", "C", 12.0, 4);

        index.insertProduct(product);

        assertNotNull(index.lookupByName("Gamma"));
    }
}
