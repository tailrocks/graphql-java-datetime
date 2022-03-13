plugins {
    `java-library`
    groovy
    id("com.adarshr.test-logger")
    id("com.tailrocks.maven-publish")
    id("com.tailrocks.signing")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(project(":graphql-java-datetime"))

    // Spring Boot
    // TODO remove hardcoded version
    compileOnly("org.springframework.boot:spring-boot-autoconfigure:2.7.0-M2")
    compileOnly("org.springframework.boot:spring-boot-starter-graphql:2.7.0-M2")
    testImplementation("org.springframework.boot:spring-boot-starter-web:2.7.0-M2")
    testImplementation("org.springframework.boot:spring-boot-starter-tomcat:2.7.0-M2")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.7.0-M2")
    testImplementation("org.springframework.boot:spring-boot-test:2.7.0-M2")

    // GraphQL Kickstart
    testImplementation(libs.graphql.kickstart.spring.boot.autoconfigure)
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
