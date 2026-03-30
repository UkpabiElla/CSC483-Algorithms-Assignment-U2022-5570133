package common;

/**
 * Simple timing helpers based on System.nanoTime().
 */
public final class PerformanceTimer {
    private PerformanceTimer() {}

    public static long timeNanos(Runnable task) {
        long start = System.nanoTime();
        task.run();
        return System.nanoTime() - start;
    }

    public static double nanosToMillis(long nanos) {
        return nanos / 1_000_000.0;
    }
}
