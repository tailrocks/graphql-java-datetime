import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    id("com.adarshr.test-logger") version Versions.gradleTestLoggerPlugin apply false
    id("io.github.gradle-nexus.publish-plugin") version Versions.gradleNexusPublishPlugin
    kotlin("jvm") version Versions.kotlin apply false
    kotlin("kapt") version Versions.kotlin apply false
    kotlin("plugin.allopen") version Versions.kotlin apply false
}

val javaVersion = 17

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(javaVersion))
    }
}

val projectVersion: String by project

allprojects {
    apply(plugin = "com.diffplug.spotless")

    apply(plugin = "idea-conventions")
    apply(plugin = "spotless-conventions")
    apply(plugin = "versions-conventions")

    version = projectVersion
    group = "com.tailrocks.graphql"

    // TODO remove me later, this is temp hack to remove deprecated dependencies from classpath
    configurations.all {
        exclude(group = "javax.validation", module = "validation-api")
    }
}

subprojects {
    apply(plugin = "java-conventions")
    apply(plugin = "junit-conventions")

    dependencies {
        // JUnit
        testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junit}")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junit}")

        // TODO remove after Spock started supports junit-jupiter engine
        testRuntimeOnly("org.junit.vintage:junit-vintage-engine:${Versions.junit}")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "8"
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            username.set(System.getenv("OSSRH_USER") ?: return@sonatype)
            password.set(System.getenv("OSSRH_PASSWORD") ?: return@sonatype)
        }
    }
}
