plugins {
    id("graphql-datetime-library-conventions")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(project(":graphql-datetime-spring-boot-common"))

    // GraphQL Kickstart
    api(libs.graphql.kickstart.spring.boot.starter)
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
