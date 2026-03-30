package com.csc483.assignment1.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple name index to speed up lookups without rebuilding the whole structure.
 */
public final class ProductIndex {
    private final Map<String, List<Product>> nameIndex = new HashMap<>();

    public static ProductIndex fromInventorySnapshot(Product[] inventorySnapshot) {
        ProductIndex index = new ProductIndex();
        if (inventorySnapshot == null) {
            return index;
        }
        for (Product inventoryRecord : inventorySnapshot) {
            if (inventoryRecord != null) {
                index.insertProduct(inventoryRecord);
            }
        }
        return index;
    }

    public void insertProduct(Product inventoryRecord) {
        if (inventoryRecord == null) {
            return;
        }
        nameIndex.computeIfAbsent(inventoryRecord.getProductName(), k -> new ArrayList<>())
                .add(inventoryRecord);
    }

    /**
     * Returns the first product with a matching name, or null if not found.
     */
    public Product lookupByName(String name) {
        if (name == null) {
            return null;
        }
        List<Product> matches = nameIndex.get(name);
        if (matches == null || matches.isEmpty()) {
            return null;
        }
        return matches.get(0);
    }

    public int size() {
        return nameIndex.size();
    }
}
