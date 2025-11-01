plugins {
    id("com.tailrocks.junit")
    id("com.tailrocks.kotlin")
}

val catalogs = extensions.getByType<VersionCatalogsExtension>()

val libs = catalogs.named("libs")

dependencies {
    testImplementation(libs.findLibrary("kotest.runner.junit").orElseThrow())
    testImplementation(libs.findLibrary("kotest.assertions.core").orElseThrow())
    //testImplementation(libs.findLibrary("kotest.framework.datatest").orElseThrow())
}
