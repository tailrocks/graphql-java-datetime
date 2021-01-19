plugins {
    `java-library`
}

dependencies {
    compile(project(":graphql-java-datetime"))
    compile("com.graphql-java-kickstart:graphql-kickstart-spring-boot-autoconfigure-tools:${Versions.graphQlSpringBoot}")
    compile("org.springframework.boot:spring-boot-autoconfigure:${Versions.springBoot}")

    testImplementation("org.springframework.boot:spring-boot-starter-web:${Versions.springBoot}")
    testImplementation("org.springframework.boot:spring-boot-starter-tomcat:${Versions.springBoot}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}")
    testImplementation("org.springframework.boot:spring-boot-test:${Versions.springBoot}")
    testImplementation("org.spockframework:spock-spring:${Versions.spock}")
    testImplementation("com.graphql-java-kickstart:graphql-spring-boot-starter:${Versions.graphQlSpringBoot}")
}
