plugins {
    `java-library`
}

dependencies {
    compile(project(":graphql-datetime-autoconfigure-common"))
    compile("com.graphql-java-kickstart:graphql-kickstart-spring-boot-starter-webflux:${Versions.graphQlSpringBoot}")
}
