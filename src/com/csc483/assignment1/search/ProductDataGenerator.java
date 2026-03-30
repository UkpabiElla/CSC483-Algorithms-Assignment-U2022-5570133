package com.csc483.assignment1.search;

import java.util.Random;

// Generates deterministic sample products for benchmarking.

public final class ProductDataGenerator {
    private static final String[] CATEGORIES = {
            "Laptops", "Phones", "Audio", "Gaming", "Accessories", "Wearables"
    };

    private ProductDataGenerator() {}

    public static Product[] buildInventorySnapshot(int count, int minId, int maxId, long seed) {
        if (count < 0) {
            throw new IllegalArgumentException("count must be non-negative");
        }
        Random deterministicMixer = new Random(seed);
        Product[] catalogSnapshot = new Product[count];
        for (int i = 0; i < count; i++) {
            int id = minId + deterministicMixer.nextInt(maxId - minId + 1);
            String name = "Product-" + id + "-" + i;
            String category = CATEGORIES[deterministicMixer.nextInt(CATEGORIES.length)];
            double price = 10.0 + deterministicMixer.nextDouble() * 990.0;
            int stockQuantity = 1 + deterministicMixer.nextInt(200);
            catalogSnapshot[i] = new Product(id, name, category, price, stockQuantity);
        }
        return catalogSnapshot;
    }

    public static Product[] orderSnapshotById(Product[] inventorySnapshot) {
        if (inventorySnapshot == null) {
            return null;
        }
        Product[] orderedSnapshot = new Product[inventorySnapshot.length];
        System.arraycopy(inventorySnapshot, 0, orderedSnapshot, 0, inventorySnapshot.length);
        orderByIdInPlace(orderedSnapshot);
        return orderedSnapshot;
    }

    // Simple insertion sort to avoid using Java built-ins for this assignment.
    private static void orderByIdInPlace(Product[] inventorySnapshot) {
        for (int i = 1; i < inventorySnapshot.length; i++) {
            Product pivot = inventorySnapshot[i];
            int j = i - 1;
            while (j >= 0 && inventorySnapshot[j].getProductId() > pivot.getProductId()) {
                inventorySnapshot[j + 1] = inventorySnapshot[j];
                j--;
            }
            inventorySnapshot[j + 1] = pivot;
        }
    }
}
