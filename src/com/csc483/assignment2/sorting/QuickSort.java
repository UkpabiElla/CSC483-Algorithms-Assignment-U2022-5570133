package com.csc483.assignment2.sorting;

import java.util.Random;

public final class QuickSort implements SortingStrategy {
    private final Random pivotPicker = new Random(123);

    @Override
    public String name() {
        return "Quick";
    }

    @Override
    public void reorderInPlace(int[] dataStream, SortObservations observations) {
        if (dataStream == null || dataStream.length < 2) {
            return;
        }
        quickSort(dataStream, 0, dataStream.length - 1, observations);
    }

    private void quickSort(int[] ledger, int lo, int hi, SortObservations observations) {
        if (lo >= hi) {
            return;
        }
        int pivotIndex = lo + pivotPicker.nextInt(hi - lo + 1);
        int pivot = ledger[pivotIndex];
        tradePositions(ledger, pivotIndex, hi, observations);

        int storeIndex = lo;
        for (int cursor = lo; cursor < hi; cursor++) {
            observations.recordDecision();
            if (ledger[cursor] <= pivot) {
                tradePositions(ledger, storeIndex, cursor, observations);
                storeIndex++;
            }
        }
        tradePositions(ledger, storeIndex, hi, observations);

        quickSort(ledger, lo, storeIndex - 1, observations);
        quickSort(ledger, storeIndex + 1, hi, observations);
    }

    private void tradePositions(int[] ledger, int left, int right, SortObservations observations) {
        if (left == right) {
            return;
        }
        int buffer = ledger[left];
        ledger[left] = ledger[right];
        ledger[right] = buffer;
        observations.recordMovement();
        observations.recordWrites(3);
    }
}
