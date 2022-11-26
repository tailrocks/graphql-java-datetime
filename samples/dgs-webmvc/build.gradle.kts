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
    implementation(project(":graphql-datetime-dgs-starter"))

    implementation("org.springframework.boot:spring-boot-starter-web")

    // DGS Framework
    implementation(platform(libs.boms.dgs))
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    testImplementation(libs.commons.text)
}
