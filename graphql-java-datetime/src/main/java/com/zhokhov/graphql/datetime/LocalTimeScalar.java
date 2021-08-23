package com.zhokhov.graphql.datetime;

import graphql.PublicApi;
import graphql.schema.GraphQLScalarType;

@PublicApi
public final class LocalTimeScalar {

    private LocalTimeScalar() {
    }

    public GraphQLScalarType create() {
        return create("LocalTime");
    }

    public GraphQLScalarType create(String name) {
        return GraphQLScalarType.newScalar()
                .name(name)
                .description("Local Time type")
                .coercing(new GraphqlLocalTimeCoercing())
                .build();
    }

}
