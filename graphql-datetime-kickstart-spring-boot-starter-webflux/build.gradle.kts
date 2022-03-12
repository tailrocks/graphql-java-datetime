plugins {
    `java-library`
    id("com.tailrocks.maven-publish")
    id("com.tailrocks.signing")
}

dependencies {
    api(project(":graphql-datetime-kickstart-autoconfigure-webflux"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
