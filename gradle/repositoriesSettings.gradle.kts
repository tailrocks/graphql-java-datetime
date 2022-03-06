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
        gradlePluginPortal()

        // uncomment if you need to use snapshot versions
        //maven("https://oss.sonatype.org/content/repositories/snapshots")
        //maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
        //maven("https://oss.jfrog.org/oss-snapshot-local")
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    // TODO temp fix for:
    //   Build was configured to prefer settings repositories over project repositories but
    //   repository 'Gradle Libs' was added by unknown code"
    // https://github.com/gradle/gradle/issues/15732
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
}
