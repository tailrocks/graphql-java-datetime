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
package graphql.datetime;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

import java.time.LocalDateTime;
import java.util.Date;

import static graphql.datetime.DateTimeHelper.parseDate;
import static graphql.datetime.DateTimeHelper.toDate;
import static graphql.datetime.DateTimeHelper.toISOString;

/**
 * @author <a href='mailto:alexey@zhokhov.com'>Alexey Zhokhov</a>
 */
public class GraphQLDate extends GraphQLScalarType {

    public GraphQLDate() {
        super("Date", "Date type", new Coercing<Date, String>() {
            private Date convertImpl(Object input) {
                if (input instanceof String) {
                    LocalDateTime localDateTime = parseDate((String) input);

                    if (localDateTime != null) {
                        return toDate(localDateTime);
                    }
                }
                return null;
            }

            @Override
            public String serialize(Object input) {
                if (input instanceof Date) {
                    return toISOString((Date) input);
                } else {
                    Date result = convertImpl(input);
                    if (result == null) {
                        throw new CoercingSerializeException("Invalid value '" + input + "' for Date");
                    }
                    return toISOString(result);
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
                if (!(input instanceof StringValue)) return null;
                String value = ((StringValue) input).getValue();
                Date result = convertImpl(value);
                return result;
            }
        });
    }

}
