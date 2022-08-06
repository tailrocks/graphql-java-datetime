plugins {
    id("com.adarshr.test-logger")
    id("graphql-datetime-library-conventions")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(project(":graphql-java-datetime"))

    // Spring Boot
    annotationProcessor(platform(libs.boms.spring.boot))
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    implementation(platform(libs.boms.spring.boot))
    implementation("org.springframework.boot:spring-boot-autoconfigure")
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
