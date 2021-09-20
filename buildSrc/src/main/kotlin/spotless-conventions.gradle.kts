plugins {
    id("com.diffplug.spotless")
}

spotless {
    java {
        licenseHeaderFile("$rootDir/gradle/licenseHeader.txt")
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
        targetExclude("**/generated/**")
    }
    kotlin {
        licenseHeaderFile("$rootDir/gradle/licenseHeader.txt")
    }
    kotlinGradle {
        ktlint()
    }
}
