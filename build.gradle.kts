import com.jfrog.bintray.gradle.BintrayExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        jcenter()
        mavenCentral()
    }
}

plugins {
    java
    idea
    `maven-publish`
    id("com.adarshr.test-logger") version Versions.gradleTestLoggerPlugin apply false
    id("net.rdrei.android.buildtimetracker") version Versions.gradleBuildTimeTrackerPlugin
    id("com.jfrog.bintray") version Versions.gradleBintrayPlugin apply false
    id("com.diffplug.spotless") version Versions.gradleSpotlessPlugin
    kotlin("jvm") version Versions.kotlin apply false
    kotlin("kapt") version Versions.kotlin apply false
    kotlin("plugin.allopen") version Versions.kotlin apply false
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

buildtimetracker {
    reporters {
        register("summary") {
            options["ordered"] = "true"
            options["barstyle"] = "none"
            options["shortenTaskNames"] = "false"
        }
    }
}

allprojects {
    apply(plugin = "idea")
    apply(plugin = "net.rdrei.android.buildtimetracker")
    apply(plugin = "com.diffplug.spotless")

    idea {
        module {
            isDownloadJavadoc = false
            isDownloadSources = false
        }
    }

    repositories {
        mavenLocal()
        gradlePluginPortal()
        jcenter()
        mavenCentral()
    }

    spotless {
        java {
            licenseHeaderFile("$rootDir/gradle/licenseHeader.txt")
            removeUnusedImports()
            trimTrailingWhitespace()
            endWithNewline()
        }
        kotlin {
            licenseHeaderFile("$rootDir/gradle/licenseHeader.txt")
        }
        kotlinGradle {
            ktlint()
        }
    }

    if (JavaVersion.current() != JavaVersion.VERSION_1_8) {
        tasks.withType<JavaCompile> {
            options.release.set(8)
        }
    }
}

val projectVersion: String by project
val projectName: String by project
val projectDescription: String by project
val projectGitRepoUrl: String by project
val projectLicense: String by project
val projectLicenseUrl: String by project

val publishingProjects = setOf(
        "graphql-datetime-autoconfigure",
        "graphql-datetime-autoconfigure-common",
        "graphql-datetime-autoconfigure-webflux",
        "graphql-datetime-spring-boot-starter",
        "graphql-datetime-spring-boot-starter-webflux",
        "graphql-java-datetime"
)

subprojects {
    apply(plugin = "java")
    apply(plugin = "com.adarshr.test-logger")
    if (publishingProjects.contains(project.name)) {
        apply(plugin = "java-library")
        apply(plugin = "maven-publish")
        apply(plugin = "com.jfrog.bintray")
    }

    version = projectVersion
    group = "com.zhokhov.graphql"

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8

        withJavadocJar()
        withSourcesJar()
    }

    dependencies {
        // JUnit
        testImplementation("org.junit.jupiter:junit-jupiter-api:${Versions.junit}")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:${Versions.junit}")
    }

    if (publishingProjects.contains(project.name)) {
        publishing {
            publications {
                create<MavenPublication>("mavenJava") {
                    from(components["java"])
                    versionMapping {
                        allVariants {
                            fromResolutionResult()
                        }
                    }
                }
            }
        }

        configure<BintrayExtension> {
            user = System.getenv("BINTRAY_USER")
            key = System.getenv("BINTRAY_KEY")
            publish = true
            override = true
            setPublications("mavenJava")
            pkg.apply {
                repo = "maven"
                name = projectName
                desc = projectDescription
                vcsUrl = projectGitRepoUrl
                setLicenses(projectLicense)
                version.apply {
                    name = projectVersion
                    gpg.apply {
                        sign = true
                    }
                }
            }
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "8"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform {
            includeEngines = setOf("junit-jupiter")
            excludeEngines = setOf("junit-vintage")
        }
    }
}
