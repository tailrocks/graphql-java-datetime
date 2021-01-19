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
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author <a href='mailto:alexey@zhokhov.com'>Alexey Zhokhov</a>
 */
public class GraphQLDate extends GraphQLScalarType {

    private static final String DEFAULT_NAME = "Date";

    public GraphQLDate() {
        this(DEFAULT_NAME);
    }

    public GraphQLDate(final String name) {
        super(name, "Date type", new Coercing<Date, String>() {
            private Date convertImpl(Object input) {
                if (input instanceof String) {
                    LocalDateTime localDateTime = DateTimeHelper.parseDate((String) input);

                    if (localDateTime != null) {
                        return DateTimeHelper.toDate(localDateTime);
                    }
                } else if (input instanceof Date) {
                    return (Date) input;
                }
                return null;
            }

            @Override
            public String serialize(Object input) {
                if (input instanceof Date) {
                    return DateTimeHelper.toISOString((Date) input);
                } else {
                    Date result = convertImpl(input);
                    if (result == null) {
                        throw new CoercingSerializeException("Invalid value '" + input + "' for Date");
                    }
                    return DateTimeHelper.toISOString(result);
                }
            }

            @Override
            public Date parseValue(Object input) {
                Date result = convertImpl(input);
                if (result == null) {
                    throw new CoercingParseValueException("Invalid value '" + input + "' for Date");
                }
                return result;
            }

            @Override
            public Date parseLiteral(Object input) {
                if (!(input instanceof StringValue)) {
                    return null;
                }
                String value = ((StringValue) input).getValue();
                return convertImpl(value);
            }
        });
    }

}
