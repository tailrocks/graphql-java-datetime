apply(from = File(settingsDir, "../gradle/repositoriesSettings.gradle.kts"))

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
