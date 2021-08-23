import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    idea
    `maven-publish`
    signing
    id("com.adarshr.test-logger") version Versions.gradleTestLoggerPlugin apply false
    id("com.diffplug.spotless") version Versions.gradleSpotlessPlugin
    id("io.github.gradle-nexus.publish-plugin") version Versions.gradleNexusPublishPlugin
    kotlin("jvm") version Versions.kotlin apply false
    kotlin("kapt") version Versions.kotlin apply false
    kotlin("plugin.allopen") version Versions.kotlin apply false
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val projectVersion: String by project
val projectName: String by project
val projectDescription: String by project
val projectLicenseShortName: String by project
val projectLicenseName: String by project
val projectLicenseUrl: String by project
val projectScmUrl: String by project
val projectScmConnection: String by project
val projectScmDeveloperConnection: String by project
val projectIssueManagementUrl: String by project

allprojects {
    apply(plugin = "idea")
    apply(plugin = "com.diffplug.spotless")

    apply(from = "${project.rootDir}/gradle/dependencyUpdates.gradle.kts")

    version = projectVersion
    group = "com.zhokhov.graphql"

    idea {
        module {
            isDownloadJavadoc = false
            isDownloadSources = false
        }
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

    if (JavaVersion.current().isJava9Compatible) {
        tasks.withType<JavaCompile> {
            options.release.set(8)
        }
    }
}

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
        apply(plugin = "signing")
    }

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

        // TODO remove after Spock started supports junit-jupiter engine
        testRuntimeOnly("org.junit.vintage:junit-vintage-engine:${Versions.junit}")
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
                    pom {
                        name.set(projectName)
                        description.set(projectDescription)
                        url.set(projectScmUrl)
                        licenses {
                            license {
                                name.set(projectLicenseName)
                                url.set(projectLicenseUrl)
                                distribution.set("repo")
                            }
                        }
                        developers {
                            developer {
                                id.set("donbeave")
                                name.set("Alexey Zhokhov")
                                email.set("alexey@zhokhov.com")
                            }
                        }
                        scm {
                            url.set(projectScmUrl)
                            connection.set(projectScmConnection)
                            developerConnection.set(projectScmDeveloperConnection)
                        }
                        issueManagement {
                            url.set(projectIssueManagementUrl)
                        }
                    }
                }
            }
        }

        signing {
            val key = System.getenv("SIGNING_KEY") ?: return@signing
            val password = System.getenv("SIGNING_PASSWORD") ?: return@signing
            val publishing: PublishingExtension by project

            useInMemoryPgpKeys(key, password)
            sign(publishing.publications)
        }
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "8"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform {
            // TODO uncomment after Spock started supports junit-jupiter engine
            // includeEngines = setOf("junit-jupiter")
            // excludeEngines = setOf("junit-vintage")
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
