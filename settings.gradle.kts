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
        // maven("https://oss.sonatype.org/content/repositories/snapshots")
        // maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
        // maven("https://oss.jfrog.org/oss-snapshot-local")
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

rootProject.name = "graphql-java-datetime"

include(
    // libraries
    ":graphql-datetime-kickstart-autoconfigure",
    ":graphql-datetime-kickstart-autoconfigure-common",
    ":graphql-datetime-kickstart-autoconfigure-webflux",
    ":graphql-datetime-kickstart-spring-boot-starter",
    ":graphql-datetime-kickstart-spring-boot-starter-webflux",
    ":graphql-java-datetime",

    // apps
    ":graphql-datetime-kickstart-sample-app",
    ":graphql-datetime-kickstart-sample-app-webflux"
)
