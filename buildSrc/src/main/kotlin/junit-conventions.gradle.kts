val isFailFast = System.getenv("GRADLE_FAIL_FAST") == null ||
        System.getenv("GRADLE_FAIL_FAST").toLowerCase() == "true"

val isParallel = System.getenv("JUNIT_PARALLEL") == "true"

tasks.withType<Test> {
    useJUnitPlatform {
        // TODO uncomment after Spock started supports junit-jupiter engine
        // includeEngines = setOf("junit-jupiter")
        // excludeEngines = setOf("junit-vintage")
    }
    failFast = isFailFast
    if (isParallel) {
        systemProperties["junit.jupiter.execution.parallel.enabled"] = true
        systemProperties["junit.jupiter.execution.parallel.mode.default"] = "concurrent"
        systemProperties["junit.jupiter.execution.parallel.mode.classes.default"] = "concurrent"
    }
}
