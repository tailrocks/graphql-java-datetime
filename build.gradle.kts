import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    alias(libs.plugins.test.logger) apply false
    alias(libs.plugins.tailrocks.spotless)
    alias(libs.plugins.tailrocks.java) apply false
    alias(libs.plugins.tailrocks.idea) apply false
    alias(libs.plugins.tailrocks.junit) apply false
    alias(libs.plugins.tailrocks.versions) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.plugin.allopen) apply false
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
    apply(plugin = "com.tailrocks.junit")

    dependencies {
        // JUnit
        implementation(platform(rootProject.libs.boms.junit))
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

        // TODO remove after Spock started supports junit-jupiter engine
        testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
    }
}
