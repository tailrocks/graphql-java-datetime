package com.zhokhov.graphql.datetime;

import graphql.PublicApi;
import graphql.schema.GraphQLScalarType;

import java.time.format.DateTimeFormatter;

@PublicApi
public final class LocalDateScalar {

    private static final String DEFAULT_NAME = "LocalDate";

    private LocalDateScalar() {
    }

    public GraphQLScalarType create() {
        return create(DEFAULT_NAME, false, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public GraphQLScalarType create(boolean zoneConversionEnabled) {
        return create(DEFAULT_NAME, zoneConversionEnabled, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public GraphQLScalarType create(String name, boolean zoneConversionEnabled, DateTimeFormatter formatter) {
        return GraphQLScalarType.newScalar()
                .name(name)
                .description("Local Date type")
                .coercing(new GraphqlLocalDateCoercing(zoneConversionEnabled, formatter))
                .build();
    }

}
