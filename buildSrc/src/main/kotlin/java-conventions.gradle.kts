plugins {
    java
}

tasks.withType<JavaCompile> {
    // FIX:
    // For queries with named parameters you need to use provide names for method parameters.
    // Use @Param for query method parameters, or when on Java 8+ use the javac flag -parameters.
    options.compilerArgs.add("-parameters")
}
