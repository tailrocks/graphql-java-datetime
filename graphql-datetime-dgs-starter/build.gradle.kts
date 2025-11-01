plugins {
    id("graphql-datetime-library-conventions")
}

dependencies {
    api(project(":graphql-datetime-dgs-autoconfigure"))

    // DGS Framework
    api(platform(libs.boms.dgs))
    api("com.netflix.graphql.dgs:graphql-dgs-spring-graphql-starter")
}
