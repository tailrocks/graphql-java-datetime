plugins {
    `java-library`
    id("com.tailrocks.maven-publish")
    id("com.tailrocks.signing")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(project(":graphql-datetime-dgs-autoconfigure"))

    // DGS Framework
    api(platform(libs.boms.dgs))
    api("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
