plugins {
    id("com.adarshr.test-logger")
    id("graphql-datetime-library-conventions")
    id("kotest-conventions")
}

dependencies {
    api(libs.graphql.java)
}
