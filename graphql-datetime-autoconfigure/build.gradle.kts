plugins {
    `java-library`
}

dependencies {
    compile(project(":graphql-datetime-autoconfigure-common"))
    compile("com.graphql-java-kickstart:graphql-spring-boot-starter:${Versions.springBoot}")
}
