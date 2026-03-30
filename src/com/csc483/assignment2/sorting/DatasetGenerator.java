package com.csc483.assignment2.sorting;

import java.util.Random;

/**
 * Dataset builders for the sorting benchmark scenarios.
 */
public final class DatasetGenerator {
    private DatasetGenerator() {}

    public static int[] randomized(int size, int bound, long seed) {
        Random sampler = new Random(seed);
        int[] recordStream = new int[size];
        for (int i = 0; i < size; i++) {
            recordStream[i] = sampler.nextInt(bound);
        }
        return recordStream;
    }

    public static int[] ascending(int size) {
        int[] recordStream = new int[size];
        for (int i = 0; i < size; i++) {
            recordStream[i] = i;
        }
        return recordStream;
    }

    public static int[] descending(int size) {
        int[] recordStream = new int[size];
        for (int i = 0; i < size; i++) {
            recordStream[i] = size - i;
        }
        return recordStream;
    }

    public static int[] nearlyOrdered(int size, int percentSorted, long seed) {
        if (percentSorted < 0 || percentSorted > 100) {
            throw new IllegalArgumentException("percentSorted must be 0..100");
        }
        int[] recordStream = ascending(size);
        int swaps = size * (100 - percentSorted) / 100;
        Random sampler = new Random(seed);
        for (int i = 0; i < swaps; i++) {
            int a = sampler.nextInt(size);
            int b = sampler.nextInt(size);
            int tmp = recordStream[a];
            recordStream[a] = recordStream[b];
            recordStream[b] = tmp;
        }
        return recordStream;
    }

    public static int[] withDuplicateKeyspace(int size, int distinctValues, long seed) {
        if (distinctValues <= 0) {
            throw new IllegalArgumentException("distinctValues must be > 0");
        }
        Random sampler = new Random(seed);
        int[] recordStream = new int[size];
        for (int i = 0; i < size; i++) {
            recordStream[i] = sampler.nextInt(distinctValues);
        }
        return recordStream;
    }
}
