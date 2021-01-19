/*
 * Copyright 2017 Alexey Zhokhov
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class GraphQLDuration extends GraphQLScalarType {

    private static final String DEFAULT_NAME = "Duration";

    public GraphQLDuration() {
        this(DEFAULT_NAME);
    }

    public GraphQLDuration(final String name) {
        super(name, "A Java 8 ISO 8601 Duration", new Coercing<Duration, String>() {
            /**
             *
             * @param input - the input string with an ISO 8601 Duration, e.g. P3Y6M4DT12H30M5S or PT1H30M
             * @return - a java.time.Duration object
             */
            private Duration convertImpl(Object input) {
                if (input instanceof String) {
                    try {
                        return Duration.parse((String) input);
                    } catch (DateTimeParseException ignored) {
                        // nothing to-do
                    }
                } else if (input instanceof Duration) {
                    return (Duration) input;
                }
                return null;
            }

            @Override
            public String serialize(Object input) {
                if (input instanceof Duration) {
                    return ((Duration) input).toString();
                } else {
                    Duration result = convertImpl(input);
                    if (result == null) {
                        throw new CoercingSerializeException("Invalid value '" + input + "' for Duration");
                    }
                    return result.toString();
                }
            }

            @Override
            public Duration parseValue(Object input) {
                Duration result = convertImpl(input);
                if (result == null) {
                    throw new CoercingParseValueException("Invalid value '" + input + "' for Duration");
                }
                return result;
            }

            @Override
            public Duration parseLiteral(Object input) {
                String value = ((StringValue) input).getValue();
                Duration result = convertImpl(value);
                if (result == null) {
                    throw new CoercingParseLiteralException("Invalid value '" + input + "' for Duration");
                }

                return result;
            }
        });
    }
}
