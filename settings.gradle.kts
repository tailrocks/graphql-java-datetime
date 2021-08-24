pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        // uncomment if you need to use snapshot versions
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        // maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
        // maven("https://oss.jfrog.org/oss-snapshot-local")
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

rootProject.name = "graphql-java-datetime"

include(
    // libraries
    ":graphql-datetime-autoconfigure",
    ":graphql-datetime-autoconfigure-common",
    ":graphql-datetime-autoconfigure-webflux",
    ":graphql-datetime-spring-boot-starter",
    ":graphql-datetime-spring-boot-starter-webflux",
    ":graphql-java-datetime",

    // apps
    ":graphql-datetime-sample-app",
    ":graphql-datetime-sample-app-webflux"
)
