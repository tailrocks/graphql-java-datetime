plugins {
    id("maven-publish-conventions")
    id("signing-conventions")
}

java {
    withJavadocJar()
    withSourcesJar()
}

dependencies {
    api(project(":graphql-datetime-autoconfigure-common"))
    api("com.graphql-java-kickstart:graphql-spring-boot-starter:${Versions.graphQlSpringBoot}")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile> {
    options.release.set(8)
}
