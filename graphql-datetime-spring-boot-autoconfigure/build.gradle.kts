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
    api(project(":graphql-java-datetime"))

    // Spring Boot
    // TODO remove hardcoded version
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor:2.7.0-M2")
    compileOnly("org.springframework.boot:spring-boot-autoconfigure:2.7.0-M2")
    compileOnly("org.springframework.boot:spring-boot-starter-graphql:2.7.0-M2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
