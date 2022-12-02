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
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Alexey Zhokhov
 */
@Internal
public class GraphqlDateCoercing implements Coercing<Date, String> {

    private final List<DateTimeFormatter> formatters = new CopyOnWriteArrayList<>();

    public GraphqlDateCoercing() {
        this(Collections.emptyList());
    }

    public GraphqlDateCoercing(Collection<DateTimeFormatter> formatters) {
        this.formatters.addAll(formatters);

        initBasicFormatters();
    }

    private void initBasicFormatters() {
        formatters.add(DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC));
        formatters.add(DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneOffset.UTC));
        formatters.add(DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneOffset.UTC));
    }

    private Date convertImpl(Object input) {
        if (input instanceof String string) {
            LocalDateTime localDateTime = parseDate(string);

            if (localDateTime != null) {
                return DateTimeHelper.toDate(localDateTime);
            }
        } else if (input instanceof Date date) {
            return date;
        }
        return null;
    }

    @Override
    public String serialize(Object input) {
        if (input instanceof Date date) {
            return DateTimeHelper.toISOString(date);
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
            throw new CoercingParseLiteralException("Expected AST type 'StringValue' but was '" + CoercingUtil.typeName(input) + "'.");
        }

        String value = ((StringValue) input).getValue();
        Date result = convertImpl(value);
        if (result == null) {
            throw new CoercingParseLiteralException("Invalid value '" + input + "' for Date");
        }

        return result;
    }

    private LocalDateTime parseDate(String date) {
        Objects.requireNonNull(date, "date");

        for (DateTimeFormatter formatter : formatters) {
            try {
                // equals ISO_LOCAL_DATE
                if (formatter.equals(formatters.get(2))) {
                    LocalDate localDate = LocalDate.parse(date, formatter);

                    return localDate.atStartOfDay();
                } else {
                    return LocalDateTime.parse(date, formatter);
                }
            } catch (java.time.format.DateTimeParseException ignored) {
            }
        }

        return null;
    }

    private String getErrorMessage(Object input) {
        return "Invalid value '" + input + "' for Date";
    }

}
