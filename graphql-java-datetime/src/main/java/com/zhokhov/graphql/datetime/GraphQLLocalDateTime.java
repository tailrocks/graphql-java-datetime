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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href='mailto:alexey@zhokhov.com'>Alexey Zhokhov</a>
 */
public class GraphQLLocalDateTime extends GraphQLScalarType {

    private static final String DEFAULT_NAME = "LocalDateTime";

    public GraphQLLocalDateTime() {
        this(DEFAULT_NAME, false);
    }

    public GraphQLLocalDateTime(boolean zoneConversionEnabled) {
        this(DEFAULT_NAME, zoneConversionEnabled, DateTimeFormatter.ISO_INSTANT);
    }

    public GraphQLLocalDateTime(final String name, boolean zoneConversionEnabled) {
        this(name, zoneConversionEnabled, DateTimeFormatter.ISO_INSTANT);
    }

    public GraphQLLocalDateTime(final String name, boolean zoneConversionEnabled, DateTimeFormatter formatter) {
        super(name != null ? name : DEFAULT_NAME, "Local Date Time type", new Coercing<LocalDateTime, String>() {
            private final LocalDateTimeConverter converter = new LocalDateTimeConverter(zoneConversionEnabled);

            private LocalDateTime convertImpl(Object input) {
                if (input instanceof String) {
                    LocalDateTime localDateTime = converter.parseDate((String) input);

                    if (localDateTime != null) {
                        return localDateTime;
                    }
                } else if (input instanceof LocalDateTime) {
                    return (LocalDateTime) input;
                }
                return null;
            }

            @Override
            public String serialize(Object input) {
                if (input instanceof LocalDateTime) {
                    return converter.formatDate((LocalDateTime) input, formatter);
                } else {
                    LocalDateTime result = convertImpl(input);
                    if (result == null) {
                        throw new CoercingSerializeException("Invalid value '" + input + "' for LocalDateTime");
                    }
                    return converter.formatDate(result, formatter);
                }
            }

            @Override
            public LocalDateTime parseValue(Object input) {
                LocalDateTime result = convertImpl(input);
                if (result == null) {
                    throw new CoercingParseValueException("Invalid value '" + input + "' for LocalDateTime");
                }
                return result;
            }

            @Override
            public LocalDateTime parseLiteral(Object input) {
                String value = ((StringValue) input).getValue();
                LocalDateTime result = convertImpl(value);
                if (result == null) {
                    throw new CoercingParseLiteralException("Invalid value '" + input + "' for LocalDateTime");
                }

                return result;
            }
        });
        if (!DateTimeHelper.DATE_FORMATTERS.contains(formatter)) {
            DateTimeHelper.DATE_FORMATTERS.add(formatter);
        }
    }

}
