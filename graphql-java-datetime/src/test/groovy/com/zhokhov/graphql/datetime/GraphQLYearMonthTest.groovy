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
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import spock.lang.Specification
import spock.lang.Unroll

import java.time.YearMonth

import static java.time.ZoneOffset.UTC

/**
 * @author Alexey Zhokhov
 */
class GraphQLYearMonthTest extends Specification {

    def setup() {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC))
    }

    @Unroll
    def "YearMonth parse literal #literal.value as #result"() {
        expect:
            new GraphqlYearMonthCoercing().parseLiteral(literal) == result

        where:
            literal                                     | result
            new StringValue('2017-07-09T11:54:42.277Z') | YearMonth.of(2017, 7)
            new StringValue('2017-07-09T13:14:45.947Z') | YearMonth.of(2017, 7)
            new StringValue('2017-07-09T11:54:42Z')     | YearMonth.of(2017, 7)
            new StringValue('2017-07-09')               | YearMonth.of(2017, 7)
    }

    @Unroll
    def "YearMonth parseLiteral throws exception for invalid #literal"() {
        when:
            new GraphqlYearMonthCoercing().parseLiteral(literal)

        then:
            thrown(CoercingParseLiteralException)

        where:
            literal                       | _
            new StringValue('')           | _
            new StringValue('not a date') | _
    }

    @Unroll
    def "YearMonth serialize #value into #result (#result.class)"() {
        expect:
            new GraphqlYearMonthCoercing().serialize(value) == result

        where:
            value                 | result
            YearMonth.of(2017, 7) | '2017-07'
    }

    @Unroll
    def "serialize throws exception for invalid input #value"() {
        when:
            new GraphqlYearMonthCoercing().serialize(value)
        then:
            thrown(CoercingSerializeException)

        where:
            value        | _
            ''           | _
            'not a date' | _
            new Object() | _
    }

    @Unroll
    def "YearMonth parse #value into #result (#result.class)"() {
        expect:
            new GraphqlYearMonthCoercing().parseValue(value) == result

        where:
            value                      | result
            '2020-07-09T11:54:42.277Z' | YearMonth.of(2020, 7)
            '2020-07-09T13:14:45.947Z' | YearMonth.of(2020, 7)
            '2020-1'                   | YearMonth.of(2020, 1)
            '2020-11'                  | YearMonth.of(2020, 11)
    }

    @Unroll
    def "parseValue throws exception for invalid input #value"() {
        when:
            new GraphqlYearMonthCoercing().parseValue(value)
        then:
            thrown(CoercingParseValueException)

        where:
            value        | _
            ''           | _
            'not a date' | _
            new Object() | _
    }

}
