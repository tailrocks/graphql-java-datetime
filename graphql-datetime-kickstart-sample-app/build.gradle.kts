plugins {
    groovy
    id("spring-conventions")
    id("com.adarshr.test-logger")
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":graphql-datetime-kickstart-spring-boot-starter"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // GraphQL Kickstart
    implementation(libs.graphql.kickstart.spring.boot.starter)

    testImplementation(libs.groovy.all)
    testImplementation(libs.spock.spring)
    testImplementation(libs.commons.text)
}
