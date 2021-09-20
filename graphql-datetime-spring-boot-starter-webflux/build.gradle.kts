plugins {
    id("maven-publish-conventions")
    id("signing-conventions")
}

dependencies {
    api(project(":graphql-datetime-autoconfigure-webflux"))
}

tasks.withType<JavaCompile>().configureEach {
    javaCompiler.set(
        javaToolchains.compilerFor {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    )
}
