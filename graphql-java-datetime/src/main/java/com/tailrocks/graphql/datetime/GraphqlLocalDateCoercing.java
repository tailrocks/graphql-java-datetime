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
package com.tailrocks.graphql.datetime;

import graphql.Internal;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Alexey Zhokhov
 */
@Internal
public class GraphqlLocalDateCoercing implements Coercing<LocalDate, String> {

    private final DateTimeFormatter formatter;
    private final LocalDateTimeConverter converter;

    public GraphqlLocalDateCoercing(boolean zoneConversionEnabled, DateTimeFormatter formatter) {
        this.formatter = formatter;
        this.converter = new LocalDateTimeConverter(zoneConversionEnabled, formatter);
    }

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
            return converter.formatDate((LocalDate) input, formatter);
        } else {
            LocalDate result = convertImpl(input);
            if (result == null) {
                throw new CoercingSerializeException("Invalid value '" + input + "' for LocalDate");
            }
            return converter.formatDate(result, formatter);
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
        String value = ((StringValue) input).getValue();
        LocalDate result = convertImpl(value);
        if (result == null) {
            throw new CoercingParseLiteralException("Invalid value '" + input + "' for LocalDate");
        }

        return result;
    }

}
