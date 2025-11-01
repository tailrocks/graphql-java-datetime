plugins {
    `java-library`
    `maven-publish`
    id("com.tailrocks.maven-publish")
    id("com.tailrocks.signing")
}

/*
java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        getByName<MavenPublication>("mavenJava") {
            pom {
                developers {
                    developer {
                        id.set("donbeave")
                        name.set("Alexey Zhokhov")
                        email.set("alexey@zhokhov.com")
                    }
                }
            }
        }
    }
}
*/
