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
    compileOnly("org.springframework.boot:spring-boot-autoconfigure")

    // DGS Framework
    compileOnly(platform(libs.boms.dgs))
    compileOnly("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
