package common;

/**
 * Small statistics helper for benchmark summaries.
 */
public final class BenchmarkStats {
    private BenchmarkStats() {}

    public static double mean(double[] values) {
        requireNonEmpty(values);
        double sum = 0.0;
        for (double v : values) {
            sum += v;
        }
        return sum / values.length;
    }

    public static double stdDev(double[] values) {
        if (values == null || values.length < 2) {
            throw new IllegalArgumentException("values must have at least 2 entries");
        }
        double mean = mean(values);
        double sumSq = 0.0;
        for (double v : values) {
            double d = v - mean;
            sumSq += d * d;
        }
        return Math.sqrt(sumSq / (values.length - 1));
    }

    // Welch's t-test for unequal variances.
    public static double tStatistic(double[] sampleA, double[] sampleB) {
        if (sampleA == null || sampleB == null || sampleA.length < 2 || sampleB.length < 2) {
            throw new IllegalArgumentException("samples must have at least 2 entries");
        }
        double meanA = mean(sampleA);
        double meanB = mean(sampleB);
        double varA = variance(sampleA);
        double varB = variance(sampleB);
        double denom = Math.sqrt(varA / sampleA.length + varB / sampleB.length);
        return (meanA - meanB) / denom;
    }

    public static double degreesOfFreedom(double[] sampleA, double[] sampleB) {
        double varA = variance(sampleA);
        double varB = variance(sampleB);
        double termA = varA / sampleA.length;
        double termB = varB / sampleB.length;
        double numerator = (termA + termB) * (termA + termB);
        double denominator = (termA * termA) / (sampleA.length - 1)
                + (termB * termB) / (sampleB.length - 1);
        return numerator / denominator;
    }

    public static double variance(double[] values) {
        if (values == null || values.length < 2) {
            throw new IllegalArgumentException("values must have at least 2 entries");
        }
        double mean = mean(values);
        double sumSq = 0.0;
        for (double v : values) {
            double d = v - mean;
            sumSq += d * d;
        }
        return sumSq / (values.length - 1);
    }

    private static void requireNonEmpty(double[] values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("values must be non-empty");
        }
    }
}
