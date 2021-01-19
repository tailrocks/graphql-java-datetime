plugins {
    groovy
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.springframework.boot") version Versions.springBoot
}

repositories {
    mavenLocal()
    jcenter()
}

dependencies {
    implementation(project(":graphql-datetime-spring-boot-starter-webflux"))

    implementation("org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}")
    implementation("com.graphql-java-kickstart:graphql-kickstart-spring-boot-starter-webflux:${Versions.graphQlSpringBoot}")
    implementation("com.graphql-java-kickstart:graphql-kickstart-spring-boot-starter-tools:${Versions.graphQlSpringBoot}")
    implementation("io.projectreactor:reactor-core:${Versions.reactorCore}")

    testImplementation("org.codehaus.groovy:groovy-all:${Versions.groovy}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.spockframework:spock-spring:${Versions.spock}")
}
