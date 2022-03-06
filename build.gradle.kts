import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java

    // https://plugins.gradle.org/plugin/com.adarshr.test-logger
    id("com.adarshr.test-logger") version "3.2.0" apply false

    // https://plugins.gradle.org/plugin/io.github.gradle-nexus.publish-plugin
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"

    kotlin("jvm") version libs.versions.kotlin.get() apply false
    kotlin("kapt") version libs.versions.kotlin.get() apply false
    kotlin("plugin.allopen") version libs.versions.kotlin.get() apply false
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
        implementation(platform(rootProject.libs.boms.junit))
        testImplementation("org.junit.jupiter:junit-jupiter-api")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

        // TODO remove after Spock started supports junit-jupiter engine
        testRuntimeOnly("org.junit.vintage:junit-vintage-engine")
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
