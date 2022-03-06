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

import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Alexey Zhokhov
 */
@Internal
public class GraphqlLocalTimeCoercing implements Coercing<LocalTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_TIME.withZone(ZoneOffset.UTC);

    private LocalTime convertImpl(Object input) {
        if (input instanceof String) {
            try {
                return LocalTime.parse((String) input, FORMATTER);
            } catch (DateTimeParseException ignored) {
                // nothing to-do
            }
        } else if (input instanceof LocalTime) {
            return (LocalTime) input;
        }
        return null;
    }

    @Override
    public String serialize(Object input) {
        if (input instanceof LocalTime) {
            return DateTimeHelper.toISOString((LocalTime) input);
        } else {
            LocalTime result = convertImpl(input);
            if (result == null) {
                throw new CoercingSerializeException("Invalid value '" + input + "' for LocalTime");
            }
            return DateTimeHelper.toISOString(result);
        }
    }

    @Override
    public LocalTime parseValue(Object input) {
        LocalTime result = convertImpl(input);
        if (result == null) {
            throw new CoercingParseValueException("Invalid value '" + input + "' for LocalTime");
        }
        return result;
    }

    @Override
    public LocalTime parseLiteral(Object input) {
        String value = ((StringValue) input).getValue();
        LocalTime result = convertImpl(value);
        if (result == null) {
            throw new CoercingParseLiteralException("Invalid value '" + input + "' for LocalTime");
        }

        return result;
    }

}
