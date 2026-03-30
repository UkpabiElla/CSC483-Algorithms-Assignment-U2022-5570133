package com.csc483.assignment2.sorting;

import common.BenchmarkStats;
import common.PerformanceTimer;

import java.util.Arrays;

public final class SortBenchmark {
    private static final int[] INPUT_SIZES = {100, 1_000, 10_000, 100_000};
    private static final int TRIALS = 5;

    public static void main(String[] args) {
        SortingStrategy[] strategies = {
                new InsertionSort(),
                new MergeSort(),
                new QuickSort()
        };

        runDataCharacterization("RANDOM", strategies, DataCharacterization.RANDOM);
        runDataCharacterization("SORTED", strategies, DataCharacterization.SORTED);
        runDataCharacterization("REVERSE", strategies, DataCharacterization.REVERSE);
        runDataCharacterization("NEARLY_SORTED", strategies, DataCharacterization.NEARLY_SORTED);
        runDataCharacterization("DUPLICATES", strategies, DataCharacterization.DUPLICATES);
    }

    private static void runDataCharacterization(String label, SortingStrategy[] strategies, DataCharacterization profile) {
        System.out.println("================================================================");
        System.out.println("SORTING STRATEGY COMPARISON - " + label + " DATA");
        System.out.println("================================================================");
        System.out.printf("%-12s %-10s %-12s %-14s %-12s%n",
                "Input Size", "Algorithm", "Time (ms)", "Decisions", "Moves");

        for (int size : INPUT_SIZES) {
            for (SortingStrategy strategy : strategies) {
                double[] responseTimes = new double[TRIALS];
                long totalDecisions = 0;
                long totalMoves = 0;

                for (int run = 0; run < TRIALS; run++) {
                    int[] dataset = profile.generateDataset(size, run);
                    int[] workingCopy = Arrays.copyOf(dataset, dataset.length);
                    SortObservations observations = new SortObservations();
                    long nanos = PerformanceTimer.timeNanos(() -> strategy.reorderInPlace(workingCopy, observations));
                    responseTimes[run] = PerformanceTimer.nanosToMillis(nanos);
                    totalDecisions += observations.getDecisionPoints();
                    totalMoves += observations.getMovementEvents();
                }

                double mean = BenchmarkStats.mean(responseTimes);
                long avgDecisions = totalDecisions / TRIALS;
                long avgMoves = totalMoves / TRIALS;
                System.out.printf("%-12d %-10s %-12.2f %-14d %-12d%n",
                        size, strategy.name(), mean, avgDecisions, avgMoves);
                System.out.println("  Runs (ms): " + formatLatencyRuns(responseTimes));
            }
        }
    }

    private static String formatLatencyRuns(double[] responseTimes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < responseTimes.length; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(String.format("%.3f", responseTimes[i]));
        }
        return sb.toString();
    }

    private enum DataCharacterization {
        RANDOM {
            @Override
            int[] generateDataset(int size, int runIndex) {
                return DatasetGenerator.randomized(size, size * 5, 100 + runIndex);
            }
        },
        SORTED {
            @Override
            int[] generateDataset(int size, int runIndex) {
                return DatasetGenerator.ascending(size);
            }
        },
        REVERSE {
            @Override
            int[] generateDataset(int size, int runIndex) {
                return DatasetGenerator.descending(size);
            }
        },
        NEARLY_SORTED {
            @Override
            int[] generateDataset(int size, int runIndex) {
                return DatasetGenerator.nearlyOrdered(size, 90, 200 + runIndex);
            }
        },
        DUPLICATES {
            @Override
            int[] generateDataset(int size, int runIndex) {
                return DatasetGenerator.withDuplicateKeyspace(size, 10, 300 + runIndex);
            }
        };

        abstract int[] generateDataset(int size, int runIndex);
    }
}
