# CSC483 Algorithms Assignment

This repository captures implementations, benchmarks, documentation, and deliverables for the CSC 483.1 assignment on algorithm design, analysis, and optimization.

## Repository layout

- `src/common` – shared utilities (`PerformanceTimer`, `BenchmarkStats`) used by both questions.
- `src/com/csc483/assignment1/search` – tech-mart inventory search helpers, data generators, hybrid index, and the search benchmark harness.
- `src/com/csc483/assignment2/sorting` – implemented sorting strategies (Insertion, Merge, Quick, Heap, Bubble), dataset generators, benchmark harness, and statistics helpers.
- `test/com/csc483/assignment1/search` & `test/com/csc483/assignment2/sorting` – JUnit suites that validate both question’s public APIs, covering normal and edge cases.
- `docs/screenshots` – generated graphs and console captures referenced by the report builder.
- `lib/` – third-party dependencies (e.g., JUnit standalone jar).

## Prerequisites

- **Java 17+** – install OpenJDK from Adoptium/Oracle and make sure javac/java are on %PATH%.
- **JUnit 5 jar** – `lib/junit-platform-console-standalone-1.10.2.jar` should be present in `lib/`.

## Build & Run

```bash
# Compile all sources
javac -d bin $(find src -name "*.java")

# Question 1 benchmark (inventory search)
java -cp bin com.csc483.assignment1.search.SearchBenchmark

# Question 2 benchmark (sorting comparison)
java -cp bin com.csc483.assignment2.sorting.SortBenchmark
```

## Testing

```bash
JUNIT_JAR=lib/junit-platform-console-standalone-1.10.2.jar
javac -d bin -cp "$JUNIT_JAR" $(find src test -name "*.java")
java -jar "$JUNIT_JAR" -cp bin --scan-classpath
```

The test suites cover edge cases such as null inputs, empty arrays, duplicate names, and ensure each sorting implementation preserves correctness.

## Benchmarks

- Captured screenshots in `docs/screenshots/`

## Notes

- Source code follows standard Java conventions (PascalCase classes, camelCase methods, specific package names).
- Statistical helpers (`common.BenchmarkStats`) compute means, standard deviations, and Welch’s t-test statistics for the empirical analysis.
