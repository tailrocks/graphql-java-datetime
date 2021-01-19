plugins {
    `java-library`
    groovy
}

dependencies {
    api("com.graphql-java:graphql-java:${Versions.graphqlJava}")

    testImplementation("org.codehaus.groovy:groovy-all:${Versions.groovy}")
    testImplementation("org.spockframework:spock-core:${Versions.spock}")
}
