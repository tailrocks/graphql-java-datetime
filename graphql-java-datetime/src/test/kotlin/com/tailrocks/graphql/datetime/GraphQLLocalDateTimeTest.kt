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
package com.tailrocks.graphql.datetime

import graphql.language.StringValue
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import io.kotest.core.spec.style.FunSpec

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter.ISO_INSTANT
import java.util.concurrent.TimeUnit.MILLISECONDS

/**
 * @author Alexey Zhokhov
 */
class GraphQLLocalDateTimeTest : FunSpec({

    /*
    def setup() {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC))
    }

    @Unroll
    def "LocalDateTime parse literal #literal.value as #result"() {
        expect:
            new GraphqlLocalDateTimeCoercing(false, ISO_INSTANT).parseLiteral(literal) == result

        where:
            literal                                     | result
            new StringValue('2017-07-09T11:54:42.277Z') | LocalDateTime.of(2017, 7, 9, 11, 54, 42, (int) MILLISECONDS.toNanos(277))
            new StringValue('2017-07-09T13:14:45.947Z') | LocalDateTime.of(2017, 7, 9, 13, 14, 45, (int) MILLISECONDS.toNanos(947))
            new StringValue('2017-07-09T11:54:42Z')     | LocalDateTime.of(2017, 7, 9, 11, 54, 42)
            new StringValue('2017-07-09T13:14:45.947')  | LocalDateTime.of(2017, 7, 9, 13, 14, 45, (int) MILLISECONDS.toNanos(947))
            new StringValue('2017-07-09T11:54:42')      | LocalDateTime.of(2017, 7, 9, 11, 54, 42)
            new StringValue('2017-07-09')               | LocalDateTime.of(LocalDate.of(2017, 7, 9), LocalTime.MIDNIGHT)
    }

    @Unroll
    def "LocalDateTime parseLiteral throws exception for invalid #literal"() {
        when:
            new GraphqlLocalDateTimeCoercing(false, ISO_INSTANT).parseLiteral(literal)

        then:
            thrown(CoercingParseLiteralException)

        where:
            literal                                | _
            new StringValue('')                    | _
            new StringValue('not a localdatetime') | _
    }

    @Unroll
    def "LocalDateTime serialize #value into #result (#result.class)"() {
        expect:
            new GraphqlLocalDateTimeCoercing(false, ISO_INSTANT).serialize(value) == result

        where:
            value                                                                     | result
            LocalDateTime.of(2017, 7, 9, 11, 54, 42, (int) MILLISECONDS.toNanos(277)) | '2017-07-09T11:54:42.277Z'
            LocalDateTime.of(2017, 7, 9, 13, 14, 45, (int) MILLISECONDS.toNanos(947)) | '2017-07-09T13:14:45.947Z'
            LocalDateTime.of(2017, 7, 9, 11, 54, 42)                                  | '2017-07-09T11:54:42Z'
            LocalDateTime.of(LocalDate.of(2017, 7, 9), LocalTime.MIDNIGHT)            | '2017-07-09T00:00:00Z'
    }

    @Unroll
    def "serialize throws exception for invalid input #value"() {
        when:
            new GraphqlLocalDateTimeCoercing(false, ISO_INSTANT).serialize(value)
        then:
            thrown(CoercingSerializeException)

        where:
            value                 | _
            ''                    | _
            'not a localdatetime' | _
            new Object()          | _
    }

    @Unroll
    def "LocalDateTime parse #value into #result (#result.class)"() {
        expect:
            new GraphqlLocalDateTimeCoercing(false, ISO_INSTANT).parseValue(value) == result

        where:
            value                      | result
            '2017-07-09T11:54:42.277Z' | LocalDateTime.of(2017, 7, 9, 11, 54, 42, (int) MILLISECONDS.toNanos(277))
            '2017-07-09T13:14:45.947Z' | LocalDateTime.of(2017, 7, 9, 13, 14, 45, (int) MILLISECONDS.toNanos(947))
            '2017-07-09T11:54:42Z'     | LocalDateTime.of(2017, 7, 9, 11, 54, 42)
            '2017-07-09T13:14:45.947'  | LocalDateTime.of(2017, 7, 9, 13, 14, 45, (int) MILLISECONDS.toNanos(947))
            '2017-07-09T11:54:42'      | LocalDateTime.of(2017, 7, 9, 11, 54, 42)
            '2017-07-09'               | LocalDateTime.of(LocalDate.of(2017, 7, 9), LocalTime.MIDNIGHT)
    }

    @Unroll
    def "parseValue throws exception for invalid input #value"() {
        when:
            new GraphqlLocalDateTimeCoercing(false, ISO_INSTANT).parseValue(value)
        then:
            thrown(CoercingParseValueException)

        where:
            value                 | _
            ''                    | _
            'not a localdatetime' | _
            new Object()          | _
    }

    @Unroll
    def "serialize #value into #result (#result.class) using zone conversion"() {
        when:
            TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.ofHours(1)))

        then:
            new GraphqlLocalDateTimeCoercing(true, ISO_INSTANT).serialize(value) == result

        where:
            value                                                                       | result
            LocalDateTime.of(2017, 7, 9, 11, 54, 42, (int) MILLISECONDS.toNanos(277))   | '2017-07-09T10:54:42.277Z'
            LocalDateTime.of(2017, 7, 9, 13, 14, 45, (int) MILLISECONDS.toNanos(947))   | '2017-07-09T12:14:45.947Z'
            LocalDateTime.of(2017, 7, 9, 11, 54, 42)                                    | '2017-07-09T10:54:42Z'
            LocalDateTime.of(LocalDate.of(2017, 7, 9), LocalTime.MIDNIGHT.plusHours(1)) | '2017-07-09T00:00:00Z'
    }

    @Unroll
    def "parse #value into #result (#result.class) using zone conversion"() {
        when:
            TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.ofHours(1)))

        then:
            new GraphqlLocalDateTimeCoercing(true, ISO_INSTANT).parseValue(value) == result

        where:
            value                      | result
            '2017-07-09T10:54:42.277Z' | LocalDateTime.of(2017, 7, 9, 11, 54, 42, (int) MILLISECONDS.toNanos(277))
            '2017-07-09T12:14:45.947Z' | LocalDateTime.of(2017, 7, 9, 13, 14, 45, (int) MILLISECONDS.toNanos(947))
            '2017-07-09T10:54:42Z'     | LocalDateTime.of(2017, 7, 9, 11, 54, 42)
            '2017-07-09'               | LocalDateTime.of(LocalDate.of(2017, 7, 9), LocalTime.MIDNIGHT)
    }

    @Unroll
    def "LocalDateTime parse #value into #result (#result.class) with custom formatter"() {
        given:
            def formatter = DateTimeFormatter.ofPattern('yyyy-MM-dd\'T\'HH:mm:ss')

        expect:
            new GraphqlLocalDateTimeCoercing(false, formatter).parseValue(value) == result

        where:
            value                 | result
            '1993-02-09T13:15:59' | LocalDateTime.of(1993, 2, 9, 13, 15, 59)
    }

    @Unroll
    def "Date serialize #value into #result (#result.class) with custom formatting"() {
        given:
            def formatter = DateTimeFormatter.ofPattern('yyyy-MM-dd\'T\'HH:mm:ss')

        expect:
            new GraphqlLocalDateTimeCoercing(false, formatter).serialize(value) == result

        where:
            value                                    | result
            LocalDateTime.of(1993, 2, 9, 13, 15, 59) | '1993-02-09T13:15:59'
    }
     */

})
