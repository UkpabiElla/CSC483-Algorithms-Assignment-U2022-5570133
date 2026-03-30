package com.csc483.assignment1.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventorySearchToolkitTest {
    @Test
    void findByIdSequentiallyFindsProduct() {
        Product[] inventorySnapshot = {
                new Product(1, "A", "C", 10.0, 2),
                new Product(2, "B", "C", 11.0, 3)
        };

        Product found = InventorySearchToolkit.findByIdSequentially(inventorySnapshot, 2);

        assertNotNull(found);
        assertEquals(2, found.getProductId());
    }

    @Test
    void binaryLookupByIdFindsProduct() {
        Product[] inventorySnapshot = {
                new Product(1, "A", "C", 10.0, 2),
                new Product(3, "B", "C", 11.0, 3),
                new Product(5, "C", "C", 12.0, 4)
        };

        Product found = InventorySearchToolkit.binaryLookupById(inventorySnapshot, 3);

        assertNotNull(found);
        assertEquals("B", found.getProductName());
    }

    @Test
    void findByNameLinearlyFindsProduct() {
        Product[] inventorySnapshot = {
                new Product(1, "Alpha", "C", 10.0, 2),
                new Product(2, "Beta", "C", 11.0, 3)
        };

        Product found = InventorySearchToolkit.findByNameLinearly(inventorySnapshot, "Beta");

        assertNotNull(found);
        assertEquals(2, found.getProductId());
    }

    @Test
    void insertProductSortedKeepsOrder() {
        Product[] inventorySnapshot = {
                new Product(1, "A", "C", 10.0, 2),
                new Product(3, "B", "C", 11.0, 3)
        };
        Product newInventoryRecord = new Product(2, "C", "C", 12.0, 4);

        Product[] updated = InventorySearchToolkit.insertProductSorted(inventorySnapshot, newInventoryRecord);

        assertEquals(3, updated.length);
        assertEquals(1, updated[0].getProductId());
        assertEquals(2, updated[1].getProductId());
        assertEquals(3, updated[2].getProductId());
    }
}
