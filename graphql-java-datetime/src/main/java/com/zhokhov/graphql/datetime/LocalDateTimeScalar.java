/*
 * Copyright 2021 Alexey Zhokhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhokhov.graphql.datetime;

import graphql.PublicApi;
import graphql.schema.GraphQLScalarType;

import java.time.format.DateTimeFormatter;

@PublicApi
public final class LocalDateTimeScalar {

    private static final String DEFAULT_NAME = "LocalDateTime";

    private LocalDateTimeScalar() {
    }

    public static GraphQLScalarType create(String name, boolean zoneConversionEnabled, DateTimeFormatter formatter) {
        return GraphQLScalarType.newScalar()
                .name(name != null ? name : "LocalDateTime")
                .description("Local Date Time type")
                .coercing(new GraphqlLocalDateTimeCoercing(zoneConversionEnabled, formatter != null ? formatter : DateTimeFormatter.ISO_INSTANT))
                .build();
    }

}
