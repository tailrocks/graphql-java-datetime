plugins {
    id("graphql-datetime-library-conventions")
}

dependencies {
    api(project(":graphql-datetime-spring-boot-autoconfigure"))

    // Spring Boot
    api("org.springframework.boot:spring-boot-starter-graphql")
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
