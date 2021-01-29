import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("com.github.ben-manes:gradle-versions-plugin:${Versions.gradleVersionsPlugin}")
    }
}

apply<com.github.benmanes.gradle.versions.VersionsPlugin>()

tasks.getByName<DependencyUpdatesTask>("dependencyUpdates") {
    resolutionStrategy {
        componentSelection {
            all {
                var rejected = listOf("alpha", "beta", "rc", "cr", "m")
                        .map { qualifier -> Regex("(?i).*[.-]$qualifier[.\\d-]*") }
                        .any { it.matches(candidate.version) }

                if (candidate.version.toLowerCase().contains("alpha") ||
                        candidate.version.toLowerCase().contains("beta") ||
                        candidate.version.toLowerCase().contains("snapshot")) {
                    rejected = true
                }

                if (!rejected) {
                    if (candidate.group == "com.sun.xml.bind" &&
                            candidate.module == "jaxb-impl" &&
                            candidate.version.contains("-b")) {
                        rejected = true
                    } else if (candidate.group == "javax.xml.bind" &&
                            candidate.module == "jaxb-api" &&
                            candidate.version.contains("-b")) {
                        rejected = true
                    } else if (candidate.group == "org.glassfish.jaxb" &&
                            candidate.module == "jaxb-runtime" &&
                            candidate.version.contains("-b")) {
                        rejected = true
                    } else if (candidate.group == "com.graphql-java" &&
                            candidate.module == "graphql-java" &&
                            candidate.version.contains("T")) {
                        rejected = true
                    } else if (candidate.group == "org.jetbrains.kotlinx" &&
                            candidate.module == "kotlinx-coroutines-core" &&
                            (candidate.version.contains("-dev") || candidate.version.contains("-eap"))) {
                        rejected = true
                    } else if (candidate.group == "org.jetbrains.kotlinx" &&
                            candidate.module == "kotlinx-serialization-runtime" &&
                            (candidate.version.contains("-dev") || candidate.version.contains("-eap"))) {
                        rejected = true
                    } else if (candidate.group == "org.hibernate.javax.persistence" &&
                            candidate.module == "hibernate-jpa-2.1-api" &&
                            candidate.version == "1.0.2") {
                        rejected = true
                    } else if (candidate.group == "org.spockframework" &&
                            candidate.module.contains("spock-") &&
                            candidate.version.contains("RC")) {
                        rejected = true
                    } else if (candidate.group == "commons-codec" &&
                            candidate.module == "commons-codec" &&
                            candidate.version.contains("200")) {
                        rejected = true
                    } else if (candidate.group == "commons-io" &&
                            candidate.module == "commons-io" &&
                            candidate.version.contains("200")) {
                        rejected = true
                    }
                }
                if (rejected) {
                    reject("Release candidate")
                }
            }
        }
    }
}
