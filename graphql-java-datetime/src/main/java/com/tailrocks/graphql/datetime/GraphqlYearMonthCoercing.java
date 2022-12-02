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

import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Efim Matytsin
 */
@Internal
public class GraphqlYearMonthCoercing implements Coercing<YearMonth, String> {

    private static final Pattern YEAR_MONTH_PATTERN = Pattern.compile("(\\d{1,4})-(\\d{1,2})");

    @Override
    public String serialize(Object input) {
        if (input instanceof YearMonth) {
            return input.toString();
        } else {
            YearMonth result = convertImpl(input);
            if (result == null) {
                throw new CoercingSerializeException(getErrorMessage(input));
            }
            return result.toString();
        }
    }

    @Override
    public YearMonth parseValue(Object input) {
        YearMonth result = convertImpl(input);
        if (result == null) {
            throw new CoercingParseValueException(getErrorMessage(input));
        }
        return result;
    }

    @Override
    public YearMonth parseLiteral(Object input) {
        if (!(input instanceof StringValue)) {
            throw new CoercingParseLiteralException("Expected AST type 'StringValue' but was '" + CoercingUtil.typeName(input) + "'.");
        }

        String value = ((StringValue) input).getValue();
        YearMonth result = convertImpl(value);
        if (result == null) {
            throw new CoercingParseLiteralException(getErrorMessage(input));
        }

        return result;
    }

    private YearMonth convertImpl(Object input) {
        if (input instanceof String) {
            Matcher m = YEAR_MONTH_PATTERN.matcher(input.toString());
            if (m.find()) {
                try {
                    return YearMonth.of(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                } catch (NumberFormatException | DateTimeException ignored) {
                    // nothing to-do
                }
            }
        } else if (input instanceof YearMonth yearMonth) {
            return yearMonth;
        }
        return null;
    }

    private String getErrorMessage(Object input) {
        return "Invalid value '" + input + "' for YearMonth";
    }

}
