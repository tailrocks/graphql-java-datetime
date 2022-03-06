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

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
