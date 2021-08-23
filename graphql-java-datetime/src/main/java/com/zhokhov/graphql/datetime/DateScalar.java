package com.zhokhov.graphql.datetime;

import graphql.PublicApi;
import graphql.schema.GraphQLScalarType;

@PublicApi
public final class DateScalar {

    private DateScalar() {
    }

    public GraphQLScalarType create() {
        return create("Date");
    }

    public GraphQLScalarType create(String name) {
        return GraphQLScalarType.newScalar()
                .name(name)
                .description("Date type")
                .coercing(new GraphqlDateCoercing())
                .build();
    }

}
