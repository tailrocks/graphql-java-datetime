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
        gradlePluginPortal()
        mavenCentral()
        // uncomment if you need to use snapshot versions
        // maven("https://oss.sonatype.org/content/repositories/snapshots")
        // maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
        // maven("https://oss.jfrog.org/oss-snapshot-local")
    }
    //repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}
