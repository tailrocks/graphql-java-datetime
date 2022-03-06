apply(from = File(settingsDir, "gradle/repositoriesSettings.gradle.kts"))

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
