plugins {
    id("graphql-datetime-library-conventions")
}

dependencies {
    api(project(":graphql-datetime-spring-boot-autoconfigure"))

    // Spring Boot
    api(platform(libs.boms.spring.boot))
    api("org.springframework.boot:spring-boot-starter-graphql")
}
