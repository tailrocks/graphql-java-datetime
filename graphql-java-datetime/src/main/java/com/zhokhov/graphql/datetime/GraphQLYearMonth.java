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

import java.time.DateTimeException;
import java.time.YearMonth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Efim Matytsin
 */
public class GraphQLYearMonth extends GraphQLScalarType {

    private static final String DEFAULT_NAME = "YearMonth";

    private static final Pattern YEAR_MONTH_PATTERN = Pattern.compile("(\\d{1,4})-(\\d{1,2})");

    public GraphQLYearMonth() {
        this(DEFAULT_NAME);
    }

    public GraphQLYearMonth(final String name) {
        super(name, "YearMonth type", new Coercing<YearMonth, String>() {

            @Override
            public String serialize(Object input) {
                if (input == null) {
                    return null;
                }

                if(!(input instanceof YearMonth)) {
                    throw new CoercingSerializeException("Invalid value '" + input + "' for YearMonth");
                }

                return input.toString();
            }

            @Override
            public YearMonth parseValue(Object input) {
                if (!(input instanceof String)) {
                    throw new CoercingParseValueException("Input value '" + input + "' is not a valid YearMonth, please use format yyyy-MM");
                }

                Matcher m = YEAR_MONTH_PATTERN.matcher(input.toString());
                if (m.find()) {
                    try {
                        return YearMonth.of(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                    } catch (NumberFormatException | DateTimeException e) {
                        throw new CoercingParseValueException(e.getMessage(), e);
                    }
                } else {
                    throw new CoercingParseValueException("Input value '" + input + "' is not a valid YearMonth, please use format yyyy-MM");
                }

            }


            @Override
            public YearMonth parseLiteral(Object ast) throws CoercingParseLiteralException {
                if (ast instanceof StringValue) {
                    return parseValue(((StringValue) ast).getValue());
                }
                return null;
            }
        });
    }

}
