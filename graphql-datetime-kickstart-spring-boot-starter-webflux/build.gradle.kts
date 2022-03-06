plugins {
    id("maven-publish-conventions")
    id("signing-conventions")
}

dependencies {
    api(project(":graphql-datetime-autoconfigure-webflux"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
