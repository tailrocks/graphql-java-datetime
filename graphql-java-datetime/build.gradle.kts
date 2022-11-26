plugins {
    id("com.tailrocks.kotlin")
    id("com.adarshr.test-logger")
    id("graphql-datetime-library-conventions")
}

dependencies {
    api(libs.graphql.java)
}
