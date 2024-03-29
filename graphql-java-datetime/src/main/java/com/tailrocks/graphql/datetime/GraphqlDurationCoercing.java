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

import java.time.Duration;
import java.time.format.DateTimeParseException;

/**
 * @author Alexey Zhokhov
 */
@Internal
public class GraphqlDurationCoercing implements Coercing<Duration, String> {

    @Override
    public String serialize(Object input) {
        if (input instanceof Duration duration) {
            return duration.toString();
        } else {
            Duration result = convertImpl(input);
            if (result == null) {
                throw new CoercingSerializeException(getErrorMessage(input));
            }
            return result.toString();
        }
    }

    @Override
    public Duration parseValue(Object input) {
        Duration result = convertImpl(input);
        if (result == null) {
            throw new CoercingParseValueException(getErrorMessage(input));
        }
        return result;
    }

    @Override
    public Duration parseLiteral(Object input) {
        if (!(input instanceof StringValue)) {
            throw new CoercingParseLiteralException("Expected AST type 'StringValue' but was '" + CoercingUtil.typeName(input) + "'.");
        }

        String value = ((StringValue) input).getValue();
        Duration result = convertImpl(value);
        if (result == null) {
            throw new CoercingParseLiteralException(getErrorMessage(input));
        }

        return result;
    }

    /**
     * @param input - the input string with an ISO 8601 Duration, e.g. P3Y6M4DT12H30M5S or PT1H30M
     * @return - a java.time.Duration object
     */
    private Duration convertImpl(Object input) {
        if (input instanceof String string) {
            try {
                return Duration.parse(string);
            } catch (DateTimeParseException ignored) {
                // nothing to-do
            }
        } else if (input instanceof Duration duration) {
            return duration;
        }
        return null;
    }

    private String getErrorMessage(Object input) {
        return "Invalid value '" + input + "' for Duration";
    }

}
