package com.csc483.assignment1.search;

import common.PerformanceTimer;

import java.util.Random;

/**
 * Benchmarks the TechMart inventory search strategies.
 */
public final class SearchBenchmark {
    private static final int PRODUCT_COUNT = 100_000;
    private static final int MIN_ID = 1;
    private static final int MAX_ID = 200_000;
    private static final long SEED = 42L;

    public static void main(String[] args) {
        Product[] inventorySnapshot = ProductDataGenerator.buildInventorySnapshot(
                PRODUCT_COUNT, MIN_ID, MAX_ID, SEED);
        Product[] orderedSnapshot = ProductDataGenerator.orderSnapshotById(inventorySnapshot);

        Random samplePicker = new Random(SEED + 7);
        int searchTargetId = MIN_ID + samplePicker.nextInt(MAX_ID - MIN_ID + 1);
        int missingProductId = MAX_ID + 1;

        long sequentialBestNanoseconds = PerformanceTimer.timeNanos(() ->
                InventorySearchToolkit.findByIdSequentially(inventorySnapshot, inventorySnapshot[0].getProductId()));
        long sequentialAverageNanoseconds = PerformanceTimer.timeNanos(() ->
                InventorySearchToolkit.findByIdSequentially(inventorySnapshot, searchTargetId));
        long sequentialWorstNanoseconds = PerformanceTimer.timeNanos(() ->
                InventorySearchToolkit.findByIdSequentially(inventorySnapshot, missingProductId));

        int pivotId = orderedSnapshot[orderedSnapshot.length / 2].getProductId();
        long binaryBestNanoseconds = PerformanceTimer.timeNanos(() ->
                InventorySearchToolkit.binaryLookupById(orderedSnapshot, pivotId));
        long binaryAverageNanoseconds = PerformanceTimer.timeNanos(() ->
                InventorySearchToolkit.binaryLookupById(orderedSnapshot, searchTargetId));
        long binaryWorstNanoseconds = PerformanceTimer.timeNanos(() ->
                InventorySearchToolkit.binaryLookupById(orderedSnapshot, missingProductId));

        ProductIndex nameIndex = ProductIndex.fromInventorySnapshot(inventorySnapshot);
        String rememberedName = inventorySnapshot[inventorySnapshot.length / 3].getProductName();
        long hybridNameSearchNanoseconds = PerformanceTimer.timeNanos(() ->
                nameIndex.lookupByName(rememberedName));
        long hybridInsertNanoseconds = PerformanceTimer.timeNanos(() -> {
            Product newInventoryRecord = new Product(MAX_ID + 10, "Product-New", "Accessories", 49.99, 5);
            Product[] updatedSnapshot = InventorySearchToolkit.insertProductSorted(orderedSnapshot, newInventoryRecord);
            nameIndex.insertProduct(newInventoryRecord);
            if (updatedSnapshot.length == 0) {
                throw new IllegalStateException("insert failed");
            }
        });

        displayPerformanceSummary(
                sequentialBestNanoseconds, sequentialAverageNanoseconds, sequentialWorstNanoseconds,
                binaryBestNanoseconds, binaryAverageNanoseconds, binaryWorstNanoseconds,
                hybridNameSearchNanoseconds, hybridInsertNanoseconds);
    }

    private static void displayPerformanceSummary(long sequentialBestNanoseconds, long sequentialAverageNanoseconds,
                                                  long sequentialWorstNanoseconds, long binaryBestNanoseconds,
                                                  long binaryAverageNanoseconds, long binaryWorstNanoseconds,
                                                  long hybridNameSearchNanoseconds, long hybridInsertNanoseconds) {
        System.out.println("================================================================");
        System.out.println("TECHMART SEARCH PERFORMANCE ANALYSIS (n = " + PRODUCT_COUNT + " products)");
        System.out.println("================================================================");
        System.out.println("SEQUENTIAL SCAN (linear)");
        System.out.printf("Best Case (ID at position 0): %.3f ms%n", PerformanceTimer.nanosToMillis(sequentialBestNanoseconds));
        System.out.printf("Average Case (random ID): %.3f ms%n", PerformanceTimer.nanosToMillis(sequentialAverageNanoseconds));
        System.out.printf("Worst Case (ID not found): %.3f ms%n", PerformanceTimer.nanosToMillis(sequentialWorstNanoseconds));
        System.out.println("BINARY LOOKUP (sorted catalog)");
        System.out.printf("Best Case (target at midpoint): %.3f ms%n", PerformanceTimer.nanosToMillis(binaryBestNanoseconds));
        System.out.printf("Average Case (random ID): %.3f ms%n", PerformanceTimer.nanosToMillis(binaryAverageNanoseconds));
        System.out.printf("Worst Case (ID not found): %.3f ms%n", PerformanceTimer.nanosToMillis(binaryWorstNanoseconds));
        double relativeSpeedup = (double) sequentialAverageNanoseconds / binaryAverageNanoseconds;
        System.out.printf("PERFORMANCE IMPROVEMENT: Binary lookup is ~%.0fx faster on average%n", relativeSpeedup);
        System.out.println("HYBRID NAME SEARCH");
        System.out.printf("Average name lookup: %.3f ms%n", PerformanceTimer.nanosToMillis(hybridNameSearchNanoseconds));
        System.out.printf("Average insert into sorted catalog + index: %.3f ms%n", PerformanceTimer.nanosToMillis(hybridInsertNanoseconds));
        System.out.println("================================================================");
    }
}
