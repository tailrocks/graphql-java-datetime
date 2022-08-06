plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.tailrocks.maven.publish.conventions)
    implementation(libs.tailrocks.signing.conventions)
}
