plugins {
    id("com.tailrocks.kotlin")
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

    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation(libs.commons.text)
}
