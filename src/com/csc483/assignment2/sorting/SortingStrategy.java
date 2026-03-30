package com.csc483.assignment2.sorting;

/**
 * Simple contract for sorting strategies used in the benchmark.
 */
public interface SortingStrategy {
    String name();

    void reorderInPlace(int[] dataStream, SortObservations observations);
}
