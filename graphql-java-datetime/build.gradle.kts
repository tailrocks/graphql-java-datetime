plugins {
    groovy
    id("maven-publish-conventions")
    id("signing-conventions")
    id("com.adarshr.test-logger")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api("com.graphql-java:graphql-java:${Versions.graphqlJava}")

    testImplementation("org.codehaus.groovy:groovy-all:${Versions.groovy}")
    testImplementation("org.spockframework:spock-core:${Versions.spock}")
}

tasks.withType<JavaCompile>().configureEach {
    javaCompiler.set(
        javaToolchains.compilerFor {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    )
}
