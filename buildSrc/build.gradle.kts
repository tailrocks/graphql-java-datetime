plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.tailrocks.maven.publish.conventions)
    implementation(libs.tailrocks.signing.conventions)
    implementation(libs.tailrocks.kotlin.conventions)
    implementation(libs.tailrocks.junit.conventions)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
