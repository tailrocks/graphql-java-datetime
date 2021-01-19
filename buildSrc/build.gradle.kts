plugins {
    `kotlin-dsl`
    idea
}

repositories {
    mavenLocal()
    gradlePluginPortal()
    jcenter()
    mavenCentral()
}

idea {
    module {
        isDownloadJavadoc = false
        isDownloadSources = false
    }
}
