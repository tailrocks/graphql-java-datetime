plugins {
    id("maven-publish-conventions")
    id("signing-conventions")
}

dependencies {
    api(project(":graphql-datetime-autoconfigure-common"))
    api("com.graphql-java-kickstart:graphql-spring-boot-starter:${Versions.graphQlSpringBoot}")
}

tasks.withType<JavaCompile>().configureEach {
    javaCompiler.set(
        javaToolchains.compilerFor {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    )
}
