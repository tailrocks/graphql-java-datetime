plugins {
    id "com.adarshr.test-logger"
}

dependencies {
    compile(project(':graphql-java-datetime'))
    compile("com.graphql-java-kickstart:graphql-kickstart-spring-boot-autoconfigure-tools:$graphqlSpringBootVersion")
    compile("org.springframework.boot:spring-boot-autoconfigure:$springBootVersion")

    testImplementation "org.springframework.boot:spring-boot-starter-web:$springBootVersion"
    testImplementation "org.springframework.boot:spring-boot-starter-tomcat:$springBootVersion"
    testImplementation "org.springframework.boot:spring-boot-starter-test:$springBootVersion"
    testImplementation "org.springframework.boot:spring-boot-test:$springBootVersion"
    testImplementation "org.spockframework:spock-spring:$spockVersion"
    testImplementation "com.graphql-java-kickstart:graphql-spring-boot-starter:$graphqlSpringBootVersion"
}
