plugins {
    `kotlin-dsl`
    idea
}

repositories {
    mavenLocal()
    gradlePluginPortal()
    mavenCentral()
}

idea {
    module {
        isDownloadJavadoc = false
        isDownloadSources = false
    }
}
