plugins {
    groovy
    id("com.adarshr.test-logger")
    id("io.spring.dependency-management")
    id("org.springframework.boot")
}

the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    implementation(project(":graphql-datetime-kickstart-spring-boot-starter"))

    // FIXME - downgrade graphql-java until graphql-java-kickstart release v13 (with graphql-java 18.x support)
    implementation("com.graphql-java:graphql-java:17.3")

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation(libs.groovy.all)
    testImplementation(libs.spock.spring)
    testImplementation(libs.commons.text)
}
