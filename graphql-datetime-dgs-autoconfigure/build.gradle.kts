plugins {
    id("com.adarshr.test-logger")
    id("graphql-datetime-library-conventions")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(project(":graphql-datetime-spring-boot-common"))

    // Spring Boot
    compileOnly(platform(libs.boms.spring.boot))
    compileOnly("org.springframework.boot:spring-boot-autoconfigure")

    // DGS Framework
    compileOnly(platform(libs.boms.dgs))
    compileOnly("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
