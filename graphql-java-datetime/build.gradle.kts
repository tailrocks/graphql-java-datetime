plugins {
    `java-library`
    groovy
    id("com.tailrocks.maven-publish")
    id("com.tailrocks.signing")
    id("com.adarshr.test-logger")
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

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
