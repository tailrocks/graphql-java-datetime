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
    compileOnly(platform(libs.boms.spring.boot))
    compileOnly("org.springframework.boot:spring-boot-starter-graphql")
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
