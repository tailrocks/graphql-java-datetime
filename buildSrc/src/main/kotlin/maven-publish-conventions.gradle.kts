plugins {
    `java-library`
    `maven-publish`
}

val projectName: String by project
val projectDescription: String by project
val projectLicenseShortName: String by project
val projectLicenseName: String by project
val projectLicenseUrl: String by project
val projectScmUrl: String by project
val projectScmConnection: String by project
val projectScmDeveloperConnection: String by project
val projectIssueManagementUrl: String by project

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
    repositories {
        maven {
            name = "Artifactory"
            setUrl("${System.getenv("ARTIFACTORY_CONTEXT_URL")}/${System.getenv("ARTIFACTORY_RELEASE_REPO_KEY")}")
            credentials {
                username = System.getenv("ARTIFACTORY_USERNAME") ?: return@credentials
                password = System.getenv("ARTIFACTORY_PASSWORD") ?: return@credentials
            }
        }
    }
}
