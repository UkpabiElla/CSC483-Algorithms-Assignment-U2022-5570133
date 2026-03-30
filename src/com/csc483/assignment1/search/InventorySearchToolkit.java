package com.csc483.assignment1.search;

/**
 * Search helpers for the TechMart inventory snapshots.
 */
public final class InventorySearchToolkit {
    private InventorySearchToolkit() {}

    public static Product findByIdSequentially(Product[] inventorySnapshot, int targetProductId) {
        if (inventorySnapshot == null) {
            return null;
        }
        for (Product inventoryRecord : inventorySnapshot) {
            if (inventoryRecord != null && inventoryRecord.getProductId() == targetProductId) {
                return inventoryRecord;
            }
        }
        return null;
    }

    public static Product binaryLookupById(Product[] inventorySnapshot, int targetProductId) {
        if (inventorySnapshot == null) {
            return null;
        }
        int low = 0;
        int high = inventorySnapshot.length - 1;
        while (low <= high) {
            int pivot = low + (high - low) / 2;
            Product inventoryRecord = inventorySnapshot[pivot];
            if (inventoryRecord == null) {
                return null;
            }
            int currentProductId = inventoryRecord.getProductId();
            if (currentProductId == targetProductId) {
                return inventoryRecord;
            }
            if (currentProductId < targetProductId) {
                low = pivot + 1;
            } else {
                high = pivot - 1;
            }
        }
        return null;
    }

    public static Product findByNameLinearly(Product[] inventorySnapshot, String targetProductName) {
        if (inventorySnapshot == null || targetProductName == null) {
            return null;
        }
        for (Product inventoryRecord : inventorySnapshot) {
            if (inventoryRecord != null && targetProductName.equals(inventoryRecord.getProductName())) {
                return inventoryRecord;
            }
        }
        return null;
    }

    public static Product[] insertProductSorted(Product[] inventorySnapshot, Product newInventoryRecord) {
        if (newInventoryRecord == null) {
            throw new IllegalArgumentException("newInventoryRecord must be non-null");
        }
        if (inventorySnapshot == null || inventorySnapshot.length == 0) {
            return new Product[] { newInventoryRecord };
        }
        int insertAt = findInsertionPoint(inventorySnapshot, newInventoryRecord.getProductId());
        Product[] ordered = new Product[inventorySnapshot.length + 1];
        System.arraycopy(inventorySnapshot, 0, ordered, 0, insertAt);
        ordered[insertAt] = newInventoryRecord;
        System.arraycopy(inventorySnapshot, insertAt, ordered, insertAt + 1, inventorySnapshot.length - insertAt);
        return ordered;
    }

    private static int findInsertionPoint(Product[] inventorySnapshot, int targetProductId) {
        int low = 0;
        int high = inventorySnapshot.length;
        while (low < high) {
            int pivot = low + (high - low) / 2;
            int currentProductId = inventorySnapshot[pivot].getProductId();
            if (currentProductId < targetProductId) {
                low = pivot + 1;
            } else {
                high = pivot;
            }
        }
        return low;
    }
}
