package com.csc483.assignment2.sorting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SortingAlgorithmsTest {
    private final SortingStrategy[] strategies = {
            new BubbleSort(),
            new InsertionSort(),
            new MergeSort(),
            new QuickSort(),
            new HeapSort()
    };

    @Test
    void strategiesSortCorrectly() {
        int[] sample = {5, 1, 4, 2, 8, 5, 3};
        for (SortingStrategy strategy : strategies) {
            int[] workingCopy = sample.clone();
            strategy.reorderInPlace(workingCopy, new SortObservations());
            assertTrue(isSorted(workingCopy), strategy.name() + " failed");
        }
    }

    @Test
    void handlesEmptyArray() {
        for (SortingStrategy strategy : strategies) {
            int[] workingCopy = new int[0];
            strategy.reorderInPlace(workingCopy, new SortObservations());
            assertEquals(0, workingCopy.length);
        }
    }

    private boolean isSorted(int[] ledger) {
        for (int i = 1; i < ledger.length; i++) {
            if (ledger[i - 1] > ledger[i]) {
                return false;
            }
        }
        return true;
    }
}
