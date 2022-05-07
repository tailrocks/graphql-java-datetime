plugins {
    `java-library`
    id("com.adarshr.test-logger")
    id("com.tailrocks.maven-publish")
    id("com.tailrocks.signing")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(project(":graphql-datetime-spring-boot-common"))

    // Spring Boot
    compileOnly("org.springframework.boot:spring-boot-starter-graphql:2.7.0-M3")
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
