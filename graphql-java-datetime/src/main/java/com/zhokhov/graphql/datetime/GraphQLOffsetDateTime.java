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

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GraphQLOffsetDateTime extends GraphQLScalarType {

    private static final String DEFAULT_NAME = "OffsetDateTime";

    public GraphQLOffsetDateTime() {
        this(DEFAULT_NAME);
    }

    public GraphQLOffsetDateTime(final String name) {
        super(name, "A Java OffsetDateTime", new Coercing<OffsetDateTime, String>() {
            private OffsetDateTime convertImpl(Object input) {
                if (input instanceof String) {
                    try {
                        return OffsetDateTime.parse((String) input, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                    } catch (DateTimeParseException ignored) {
                        // nothing to-do
                    }
                } else if (input instanceof OffsetDateTime) {
                    return (OffsetDateTime) input;
                }
                return null;
            }

            @Override
            public String serialize(Object input) {
                if (input instanceof OffsetDateTime) {
                    return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format((OffsetDateTime) input);
                } else {
                    OffsetDateTime result = convertImpl(input);
                    if (result == null) {
                        throw new CoercingSerializeException("Invalid value '" + input + "' for OffsetDateTime");
                    }
                    return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(result);
                }
            }

            @Override
            public OffsetDateTime parseValue(Object input) {
                OffsetDateTime result = convertImpl(input);
                if (result == null) {
                    throw new CoercingParseValueException("Invalid value '" + input + "' for OffsetDateTime");
                }
                return result;
            }

            @Override
            public OffsetDateTime parseLiteral(Object input) {
                String value = ((StringValue) input).getValue();
                OffsetDateTime result = convertImpl(value);
                if (result == null) {
                    throw new CoercingParseLiteralException("Invalid value '" + input + "' for OffsetDateTime");
                }

                return result;
            }
        });
    }

}
