package com.zhokhov.graphql.datetime;

import graphql.PublicApi;
import graphql.schema.GraphQLScalarType;

@PublicApi
public final class OffsetDateTimeScalar {

    private OffsetDateTimeScalar() {
    }

    public GraphQLScalarType create() {
        return create("OffsetDateTime");
    }

    public GraphQLScalarType create(String name) {
        return GraphQLScalarType.newScalar()
                .name(name)
                .description("A Java OffsetDateTime")
                .coercing(new GraphqlOffsetDateTimeCoercing())
                .build();
    }

}
