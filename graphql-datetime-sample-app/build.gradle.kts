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
    implementation(project(":graphql-datetime-spring-boot-starter"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:${Versions.graphQlSpringBoot}")
    implementation("com.graphql-java-kickstart:graphiql-spring-boot-starter:${Versions.graphQlSpringBoot}")

    testImplementation("org.codehaus.groovy:groovy-all:${Versions.groovy}")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.spockframework:spock-spring:${Versions.spock}")
}
