plugins {
    id("com.adarshr.test-logger")
    id("graphql-datetime-library-conventions")
}

dependencies {
    api(project(":graphql-datetime-spring-boot-common"))

    // Spring Boot
    compileOnly(platform(libs.boms.spring.boot))
    compileOnly("org.springframework.boot:spring-boot-starter-graphql")
}
