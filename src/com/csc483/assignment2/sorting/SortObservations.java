package com.csc483.assignment2.sorting;

/**
 * Captures basic operation counts for comparisons and data movement.
 */
public final class SortObservations {
    private long decisionPoints;
    private long movementEvents;
    private long writeOperations;

    public void recordDecision() {
        decisionPoints++;
    }

    public void recordMovement() {
        movementEvents++;
    }

    public void recordWrites(long count) {
        writeOperations += count;
    }

    public long getDecisionPoints() {
        return decisionPoints;
    }

    public long getMovementEvents() {
        return movementEvents;
    }

    public long getWriteOperations() {
        return writeOperations;
    }

    @Override
    public String toString() {
        return "decisions=" + decisionPoints + ", moves=" + movementEvents + ", writes=" + writeOperations;
    }
}
