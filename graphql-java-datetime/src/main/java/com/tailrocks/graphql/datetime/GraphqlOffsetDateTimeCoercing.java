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
package com.tailrocks.graphql.datetime;

import graphql.Internal;
import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Alexey Zhokhov
 */
@Internal
public class GraphqlOffsetDateTimeCoercing implements Coercing<OffsetDateTime, String> {

    private OffsetDateTime convertImpl(Object input) {
        if (input instanceof String string) {
            try {
                return OffsetDateTime.parse(string, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            } catch (DateTimeParseException ignored) {
                // nothing to-do
            }
        } else if (input instanceof OffsetDateTime offsetDateTime) {
            return offsetDateTime;
        }
        return null;
    }

    @Override
    public String serialize(Object input) {
        if (input instanceof OffsetDateTime offsetDateTime) {
            return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(offsetDateTime);
        } else {
            OffsetDateTime result = convertImpl(input);
            if (result == null) {
                throw new CoercingSerializeException(getErrorMessage(input));
            }
            return DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(result);
        }
    }

    @Override
    public OffsetDateTime parseValue(Object input) {
        OffsetDateTime result = convertImpl(input);
        if (result == null) {
            throw new CoercingParseValueException(getErrorMessage(input));
        }
        return result;
    }

    @Override
    public OffsetDateTime parseLiteral(Object input) {
        if (!(input instanceof StringValue)) {
            throw new CoercingParseLiteralException("Expected AST type 'StringValue' but was '" + CoercingUtil.typeName(input) + "'.");
        }

        String value = ((StringValue) input).getValue();
        OffsetDateTime result = convertImpl(value);
        if (result == null) {
            throw new CoercingParseLiteralException(getErrorMessage(input));
        }

        return result;
    }

    private String getErrorMessage(Object input) {
        return "Invalid value '" + input + "' for OffsetDateTime";
    }

}
