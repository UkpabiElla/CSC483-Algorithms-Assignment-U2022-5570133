package com.csc483.assignment2.sorting;

public final class InsertionSort implements SortingStrategy {
    @Override
    public String name() {
        return "Insertion";
    }

    @Override
    public void reorderInPlace(int[] dataStream, SortObservations observations) {
        if (dataStream == null) {
            return;
        }
        for (int partition = 1; partition < dataStream.length; partition++) {
            int pivot = dataStream[partition];
            observations.recordWrites(1);
            int cursor = partition - 1;
            while (cursor >= 0) {
                observations.recordDecision();
                if (dataStream[cursor] > pivot) {
                    dataStream[cursor + 1] = dataStream[cursor];
                    observations.recordWrites(1);
                    observations.recordMovement();
                    cursor--;
                } else {
                    break;
                }
            }
            dataStream[cursor + 1] = pivot;
            observations.recordWrites(1);
            observations.recordMovement();
        }
    }
}
