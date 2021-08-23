package com.zhokhov.graphql.datetime;

import graphql.PublicApi;
import graphql.schema.GraphQLScalarType;

@PublicApi
public final class DurationScalar {

    private DurationScalar() {
    }

    public GraphQLScalarType create() {
        return create("Duration");
    }

    public GraphQLScalarType create(String name) {
        return GraphQLScalarType.newScalar()
                .name(name)
                .description("A Java 8 ISO 8601 Duration")
                .coercing(new GraphqlDurationCoercing())
                .build();
    }

}
