/*
 *  Copyright 2017 Alexey Zhokhov
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.zhokhov.graphql.datetime;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @author <a href='mailto:alexey@zhokhov.com'>Alexey Zhokhov</a>
 */
public class GraphQLLocalDate extends GraphQLScalarType {

    private static final String DEFAULT_NAME = "LocalDate";

    public GraphQLLocalDate() {
        this(DEFAULT_NAME, false, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public GraphQLLocalDate(boolean zoneConversionEnabled) {
        this(DEFAULT_NAME, zoneConversionEnabled, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public GraphQLLocalDate(final String name, boolean zoneConversionEnabled) {
        this(name, zoneConversionEnabled, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public GraphQLLocalDate(final String name, boolean zoneConversionEnabled, DateTimeFormatter formatter) {
        super(name != null ? name : DEFAULT_NAME, "Local Date type", new Coercing<LocalDate, String>() {
            private LocalDateTimeConverter converter = new LocalDateTimeConverter(zoneConversionEnabled);

            private LocalDate convertImpl(Object input) {
                if (input instanceof String) {
                    LocalDateTime localDateTime = converter.parseDate((String) input);

                    if (localDateTime != null) {
                        return localDateTime.toLocalDate();
                    }
                } else if (input instanceof LocalDate) {
                    return (LocalDate) input;
                }
                return null;
            }

            @Override
            public String serialize(Object input) {
                if (input instanceof LocalDate) {
                    return formatter.format((LocalDate) input);
                } else {
                    LocalDate result = convertImpl(input);
                    if (result == null) {
                        throw new CoercingSerializeException("Invalid value '" + input + "' for LocalDate");
                    }
                    return formatter.format(result);
                }
            }

            @Override
            public LocalDate parseValue(Object input) {
                LocalDate result = convertImpl(input);
                if (result == null) {
                    throw new CoercingParseValueException("Invalid value '" + input + "' for LocalDate");
                }
                return result;
            }

            @Override
            public LocalDate parseLiteral(Object input) {
                if (!(input instanceof StringValue)) return null;
                String value = ((StringValue) input).getValue();
                return convertImpl(value);
            }
        });
        DateTimeHelper.DATE_FORMATTERS.add(formatter);
    }
}
