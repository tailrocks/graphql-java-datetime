plugins {
    groovy
    id("com.adarshr.test-logger")
    id("maven-publish-conventions")
    id("signing-conventions")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(project(":graphql-java-datetime"))

    // Spring Boot
    api(platform(libs.boms.spring.boot))
    api("org.springframework.boot:spring-boot-autoconfigure")
    testImplementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-tomcat")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-test")

    // GraphQL Kickstart
    api(libs.graphql.kickstart.spring.boot.autoconfigure)
    testImplementation(libs.graphql.kickstart.spring.boot.starter)

    testImplementation(libs.groovy.all)
    testImplementation(libs.spock.spring)
    testImplementation(libs.commons.text)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
