plugins {
    `java-library`
}

dependencies {
    api(project(":graphql-datetime-autoconfigure-common"))
    api("com.graphql-java-kickstart:graphql-spring-boot-starter:${Versions.graphQlSpringBoot}")
}
