plugins {
    id("com.adarshr.test-logger")
    id("io.spring.dependency-management")
    id("org.springframework.boot")
    id("kotest-conventions")
}

the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    implementation(project(":graphql-datetime-spring-boot-autoconfigure"))

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-graphql")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation(libs.commons.text)

    // Kotest
    testImplementation(libs.kotest.extensions.spring)

    // Jackson
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
}
