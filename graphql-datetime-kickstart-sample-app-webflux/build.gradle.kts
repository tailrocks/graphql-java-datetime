plugins {
    groovy
    id("spring-conventions")
    id("com.adarshr.test-logger")
    id("org.springframework.boot")
}

dependencies {
    implementation(project(":graphql-datetime-kickstart-spring-boot-starter-webflux"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation(libs.graphql.kickstart.spring.boot.starter)

    implementation("io.projectreactor:reactor-core")

    testImplementation(libs.groovy.all)
    testImplementation(libs.spock.spring)
    testImplementation(libs.commons.text)
}
