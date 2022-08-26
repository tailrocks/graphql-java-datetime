plugins {
    groovy
    id("com.adarshr.test-logger")
    id("graphql-datetime-library-conventions")
}

dependencies {
    api(libs.graphql.java)

    testImplementation(libs.groovy.all)
    testImplementation(libs.spock.core)
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
