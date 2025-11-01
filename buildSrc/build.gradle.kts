plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

dependencies {
    implementation(libs.tailrocks.maven.publish.conventions)
    implementation(libs.tailrocks.signing.conventions)
    implementation(libs.tailrocks.kotlin.conventions)
    implementation(libs.tailrocks.junit.conventions)
}
