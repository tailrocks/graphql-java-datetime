plugins {
    id("graphql-datetime-library-conventions")
}

dependencies {
    api(project(":graphql-datetime-spring-boot-common"))

    // GraphQL Kickstart
    api(libs.graphql.kickstart.spring.boot.starter)
}
