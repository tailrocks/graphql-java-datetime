package com.zhokhov.graphql.datetime;

import graphql.PublicApi;
import graphql.schema.GraphQLScalarType;

@PublicApi
public final class YearMonthScalar {

    private YearMonthScalar() {
    }

    public GraphQLScalarType create() {
        return create("YearMonth");
    }

    public GraphQLScalarType create(String name) {
        return GraphQLScalarType.newScalar()
                .name(name)
                .description("YearMonth type")
                .coercing(new GraphqlYearMonthCoercing())
                .build();
    }

}
