plugins {
    groovy
    id("spring-conventions")
    id("com.adarshr.test-logger")
}

dependencies {
    implementation(project(":graphql-datetime-spring-boot-starter-webflux"))

    implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:${Versions.graphQlSpringBoot}")
    implementation("io.projectreactor:reactor-core:${Versions.reactorCore}")

    testImplementation("org.codehaus.groovy:groovy-all:${Versions.groovy}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.spockframework:spock-spring:${Versions.spock}")
}
