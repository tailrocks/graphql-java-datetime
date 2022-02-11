plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("com.github.ben-manes:gradle-versions-plugin:0.39.0")
    implementation("org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:3.3")
    implementation("io.spring.gradle:dependency-management-plugin:1.0.11.RELEASE")
    implementation("org.springframework.boot:spring-boot-gradle-plugin:2.5.4")
    implementation("com.diffplug.spotless:spotless-plugin-gradle:5.17.1")
}
