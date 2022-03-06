plugins {
    groovy
    id("spring-conventions")
    id("com.adarshr.test-logger")
}

dependencies {
    implementation(project(":graphql-datetime-spring-boot-starter"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:${Versions.graphQlSpringBoot}")

    testImplementation("org.codehaus.groovy:groovy-all:${Versions.groovy}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.spockframework:spock-spring:${Versions.spock}")
}
