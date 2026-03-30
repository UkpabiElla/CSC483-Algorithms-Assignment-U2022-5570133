package com.csc483.assignment2.sorting;

public final class MergeSort implements SortingStrategy {
    @Override
    public String name() {
        return "Merge";
    }

    @Override
    public void reorderInPlace(int[] dataStream, SortObservations observations) {
        if (dataStream == null || dataStream.length < 2) {
            return;
        }
        int[] aux = new int[dataStream.length];
        mergeSort(dataStream, aux, 0, dataStream.length - 1, observations);
    }

    private void mergeSort(int[] ledger, int[] aux, int lo, int hi, SortObservations observations) {
        if (lo >= hi) {
            return;
        }
        int mid = lo + (hi - lo) / 2;
        mergeSort(ledger, aux, lo, mid, observations);
        mergeSort(ledger, aux, mid + 1, hi, observations);
        merge(ledger, aux, lo, mid, hi, observations);
    }

    private void merge(int[] ledger, int[] aux, int lo, int mid, int hi, SortObservations observations) {
        for (int i = lo; i <= hi; i++) {
            aux[i] = ledger[i];
            observations.recordWrites(1);
            observations.recordMovement();
        }
        int left = lo;
        int right = mid + 1;
        int write = lo;
        while (left <= mid && right <= hi) {
            observations.recordDecision();
            if (aux[left] <= aux[right]) {
                ledger[write++] = aux[left++];
            } else {
                ledger[write++] = aux[right++];
            }
            observations.recordWrites(1);
            observations.recordMovement();
        }
        while (left <= mid) {
            ledger[write++] = aux[left++];
            observations.recordWrites(1);
            observations.recordMovement();
        }
        while (right <= hi) {
            ledger[write++] = aux[right++];
            observations.recordWrites(1);
            observations.recordMovement();
        }
    }
}
