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
package com.zhokhov.graphql.datetime

import graphql.language.StringValue
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import spock.lang.Specification
import spock.lang.Unroll

import java.time.*

import static java.util.concurrent.TimeUnit.MILLISECONDS

/**
 * @author <ahref='mailto:alexey@zhokhov.com'   >   Alexey Zhokhov</a>
 */
class GraphQLOffsetDateTimeTest extends Specification {

    @Unroll
    def "Date parse literal #literal.value as #result"() {
        expect:
        new GraphQLOffsetDateTime().getCoercing().parseLiteral(literal) == result

        where:
        literal                                          | result
        new StringValue('2017-07-09T11:54:42.277Z')      | OffsetDateTime.of(2017, 7, 9, 11, 54, 42, (int) MILLISECONDS.toNanos(277), ZoneOffset.UTC)
        new StringValue('2017-07-09T13:14:45.947+02:00') | OffsetDateTime.of(2017, 7, 9, 13, 14, 45, (int) MILLISECONDS.toNanos(947), ZoneOffset.ofHours(2))
        new StringValue('2017-07-09T11:54:42Z')          | OffsetDateTime.of(LocalDateTime.of(2017, 7, 9, 11, 54, 42), ZoneOffset.UTC)
    }

    @Unroll
    def "Date returns null for invalid #literal"() {
        expect:
        new GraphQLOffsetDateTime().getCoercing().parseLiteral(literal) == null

        where:
        literal                       | _
        new StringValue("not a date") | _
    }

    @Unroll
    def "Date serialize #value into #result (#result.class)"() {
        expect:
        new GraphQLOffsetDateTime().getCoercing().serialize(value) == result

        where:
        value                                                                                             | result
        OffsetDateTime.of(2017, 7, 9, 11, 54, 42, (int) MILLISECONDS.toNanos(277), ZoneOffset.UTC)        | '2017-07-09T11:54:42.277Z'
        OffsetDateTime.of(2017, 7, 9, 13, 14, 45, (int) MILLISECONDS.toNanos(947), ZoneOffset.ofHours(2)) | '2017-07-09T13:14:45.947+02:00'
        OffsetDateTime.of(LocalDateTime.of(2017, 7, 9, 11, 54, 42), ZoneOffset.UTC)                       | '2017-07-09T11:54:42Z'
        OffsetDateTime.of(LocalDate.of(2017, 7, 9), LocalTime.MIDNIGHT, ZoneOffset.UTC)                   | '2017-07-09T00:00:00Z'
    }

    @Unroll
    def "serialize throws exception for invalid input #value"() {
        when:
        new GraphQLOffsetDateTime().getCoercing().serialize(value)
        then:
        thrown(CoercingSerializeException)

        where:
        value        | _
        ''           | _
        'not a date' | _
        new Object() | _
    }

    @Unroll
    def "Date parse #value into #result (#result.class)"() {
        expect:
        new GraphQLOffsetDateTime().getCoercing().parseValue(value) == result

        where:
        value                           | result
        '2017-07-09T11:54:42.277Z'      | OffsetDateTime.of(2017, 7, 9, 11, 54, 42, (int) MILLISECONDS.toNanos(277), ZoneOffset.UTC)
        '2017-07-09T13:14:45.947+02:00' | OffsetDateTime.of(2017, 7, 9, 13, 14, 45, (int) MILLISECONDS.toNanos(947), ZoneOffset.ofHours(2))
        '2017-07-09T11:54:42Z'          | OffsetDateTime.of(LocalDateTime.of(2017, 7, 9, 11, 54, 42), ZoneOffset.UTC)
    }

    @Unroll
    def "parseValue throws exception for invalid input #value"() {
        when:
        new GraphQLOffsetDateTime().getCoercing().parseValue(value)
        then:
        thrown(CoercingParseValueException)

        where:
        value        | _
        ''           | _
        'not a date' | _
        new Object() | _
    }
}
