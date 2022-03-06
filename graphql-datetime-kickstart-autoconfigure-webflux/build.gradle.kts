plugins {
    id("maven-publish-conventions")
    id("signing-conventions")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(project(":graphql-datetime-kickstart-autoconfigure-common"))
    api(libs.graphql.kickstart.spring.boot.starter)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
