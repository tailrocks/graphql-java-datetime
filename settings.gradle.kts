apply(from = File(settingsDir, "gradle/repositoriesSettings.gradle.kts"))

rootProject.name = "graphql-java-datetime"

include(
    // libraries
    // "graphql-datetime-dgs-autoconfigure",
    // "graphql-datetime-dgs-starter",
    "graphql-datetime-kickstart-spring-boot-starter",
    "graphql-datetime-spring-boot-autoconfigure",
    "graphql-datetime-spring-boot-common",
    "graphql-datetime-spring-boot-starter",
    "graphql-java-datetime",

    // samples
    "samples:kickstart-webflux",
    "samples:kickstart-webmvc",
    "samples:spring-boot-webmvc"
    // "samples:dgs-webmvc"
)
