package com.csc483.assignment2.sorting;

public final class BubbleSort implements SortingStrategy {
    @Override
    public String name() {
        return "Bubble";
    }

    @Override
    public void reorderInPlace(int[] dataStream, SortObservations observations) {
        if (dataStream == null) {
            return;
        }
        int horizon = dataStream.length;
        for (int pass = 0; pass < horizon - 1; pass++) {
            boolean hasSwapped = false;
            for (int cursor = 0; cursor < horizon - pass - 1; cursor++) {
                observations.recordDecision();
                if (dataStream[cursor] > dataStream[cursor + 1]) {
                    exchangePositions(dataStream, cursor, cursor + 1, observations);
                    hasSwapped = true;
                }
            }
            if (!hasSwapped) {
                break;
            }
        }
    }

    private void exchangePositions(int[] dataStream, int left, int right, SortObservations observations) {
        int buffer = dataStream[left];
        dataStream[left] = dataStream[right];
        dataStream[right] = buffer;
        observations.recordMovement();
        observations.recordWrites(3);
    }
}
