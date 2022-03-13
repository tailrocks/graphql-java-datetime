apply(from = File(settingsDir, "gradle/repositoriesSettings.gradle.kts"))

rootProject.name = "graphql-java-datetime"

include(
    // libraries
    "graphql-datetime-kickstart-spring-boot-starter",
    "graphql-datetime-spring-boot-autoconfigure",
    "graphql-datetime-spring-boot-starter",
    "graphql-java-datetime",

    // samples
    "samples:kickstart-webmvc",
    "samples:kickstart-webflux",
    "samples:spring-boot-webmvc"
)
