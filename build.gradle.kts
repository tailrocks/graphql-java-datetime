plugins {
    alias(libs.plugins.tailrocks.java)
    alias(libs.plugins.tailrocks.spotless)
    alias(libs.plugins.tailrocks.idea) apply false
    alias(libs.plugins.tailrocks.versions) apply false
    alias(libs.plugins.test.logger) apply false
    alias(libs.plugins.spring.dependency.management) apply false
    alias(libs.plugins.spring.boot) apply false
}

val projectVersion: String by project

allprojects {
    apply(plugin = "com.tailrocks.idea")
    apply(plugin = "com.tailrocks.spotless")
    apply(plugin = "com.tailrocks.versions")

    version = projectVersion
    group = "com.tailrocks.graphql"

    spotless {
        java {
            licenseHeaderFile("$rootDir/gradle/licenseHeader.txt")
        }
        kotlin {
            licenseHeaderFile("$rootDir/gradle/licenseHeader.txt")
        }
    }

    // TODO remove me later, this is temp hack to remove deprecated dependencies from classpath
    configurations.all {
        exclude(group = "javax.validation", module = "validation-api")
    }
}

subprojects {
    apply(plugin = "com.tailrocks.java")
}
