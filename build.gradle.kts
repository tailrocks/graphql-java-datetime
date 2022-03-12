import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java

    // https://plugins.gradle.org/plugin/com.adarshr.test-logger
    id("com.adarshr.test-logger") version "3.2.0" apply false

    // https://plugins.gradle.org/plugin/io.github.gradle-nexus.publish-plugin
    id("io.github.gradle-nexus.publish-plugin") version "1.1.0"

    // https://plugins.gradle.org/plugin/com.tailrocks.java
    id("com.tailrocks.java") version "0.1.3" apply false

    // https://plugins.gradle.org/plugin/com.tailrocks.idea
    id("com.tailrocks.idea") version "0.1.3" apply false

    // https://plugins.gradle.org/plugin/com.tailrocks.junit
    id("com.tailrocks.junit") version "0.1.4" apply false

    // https://plugins.gradle.org/plugin/com.tailrocks.versions
    id("com.tailrocks.versions") version "0.1.3" apply false

    // https://plugins.gradle.org/plugin/com.tailrocks.maven-publish
    id("com.tailrocks.maven-publish") version "0.1.6" apply false

    // https://plugins.gradle.org/plugin/com.tailrocks.spotless
    id("com.tailrocks.spotless") version "0.1.3" apply false

    // https://plugins.gradle.org/plugin/com.tailrocks.signing
    id("com.tailrocks.signing") version "0.1.3" apply false

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
    apply(plugin = "com.tailrocks.idea")
    apply(plugin = "com.tailrocks.spotless")
    apply(plugin = "com.tailrocks.versions")

    version = projectVersion
    group = "com.tailrocks.graphql"

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
