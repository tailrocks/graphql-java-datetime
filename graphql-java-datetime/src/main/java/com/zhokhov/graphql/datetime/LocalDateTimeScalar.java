package com.zhokhov.graphql.datetime;

import graphql.PublicApi;
import graphql.schema.GraphQLScalarType;

import java.time.format.DateTimeFormatter;

@PublicApi
public final class LocalDateTimeScalar {

    private static final String DEFAULT_NAME = "LocalDateTime";

    private LocalDateTimeScalar() {
    }

    public GraphQLScalarType create() {
        return create(DEFAULT_NAME, false, DateTimeFormatter.ISO_INSTANT);
    }

    public GraphQLScalarType create(boolean zoneConversionEnabled) {
        return create(DEFAULT_NAME, zoneConversionEnabled, DateTimeFormatter.ISO_INSTANT);
    }

    public GraphQLScalarType create(String name, boolean zoneConversionEnabled, DateTimeFormatter formatter) {
        return GraphQLScalarType.newScalar()
                .name(name)
                .description("Local Date Time type")
                .coercing(new GraphqlLocalDateTimeCoercing(zoneConversionEnabled, formatter))
                .build();
    }

}
