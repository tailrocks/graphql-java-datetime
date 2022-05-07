plugins {
    `java-library`
    id("com.tailrocks.maven-publish")
    id("com.tailrocks.signing")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(project(":graphql-datetime-spring-boot-autoconfigure"))

    // Spring Boot
    api("org.springframework.boot:spring-boot-starter-graphql:2.7.0-RC1")
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
