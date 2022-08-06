plugins {
    groovy
    id("com.adarshr.test-logger")
    id("graphql-datetime-library-conventions")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(libs.graphql.java)

    testImplementation(libs.groovy.all)
    testImplementation(libs.spock.core)
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
