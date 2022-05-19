pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()

        // uncomment if you need to use pre-release or snapshot versions
        //maven("https://repo.spring.io/milestone")
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()

        // uncomment if you need to use pre-release or snapshot versions
        //maven("https://repo.spring.io/milestone")
        //maven("https://oss.sonatype.org/content/repositories/snapshots")
        //maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
        //maven("https://oss.jfrog.org/oss-snapshot-local")
    }
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}
