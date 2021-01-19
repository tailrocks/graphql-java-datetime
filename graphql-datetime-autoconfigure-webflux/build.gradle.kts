plugins {
    `java-library`
}

dependencies {
    api(project(":graphql-datetime-autoconfigure-common"))
    api("com.graphql-java-kickstart:graphql-kickstart-spring-boot-starter-webflux:${Versions.graphQlSpringBoot}")
}
