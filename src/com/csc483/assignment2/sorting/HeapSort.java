package com.csc483.assignment2.sorting;

public final class HeapSort implements SortingStrategy {
    @Override
    public String name() {
        return "Heap";
    }

    @Override
    public void reorderInPlace(int[] dataStream, SortObservations observations) {
        if (dataStream == null || dataStream.length < 2) {
            return;
        }
        int horizon = dataStream.length;
        for (int idx = horizon / 2 - 1; idx >= 0; idx--) {
            heapify(dataStream, horizon, idx, observations);
        }
        for (int end = horizon - 1; end > 0; end--) {
            exchangeRoot(dataStream, 0, end, observations);
            heapify(dataStream, end, 0, observations);
        }
    }

    private void heapify(int[] dataStream, int size, int root, SortObservations observations) {
        int largest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < size) {
            observations.recordDecision();
            if (dataStream[left] > dataStream[largest]) {
                largest = left;
            }
        }
        if (right < size) {
            observations.recordDecision();
            if (dataStream[right] > dataStream[largest]) {
                largest = right;
            }
        }
        if (largest != root) {
            exchangeRoot(dataStream, root, largest, observations);
            heapify(dataStream, size, largest, observations);
        }
    }

    private void exchangeRoot(int[] dataStream, int left, int right, SortObservations observations) {
        int buffer = dataStream[left];
        dataStream[left] = dataStream[right];
        dataStream[right] = buffer;
        observations.recordMovement();
        observations.recordWrites(3);
    }
}
