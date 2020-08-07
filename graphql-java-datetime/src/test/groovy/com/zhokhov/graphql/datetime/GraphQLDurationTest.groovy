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

import java.time.Duration
import java.time.YearMonth

import static com.zhokhov.graphql.datetime.DateTimeHelper.createDate

/**
 * @author <ahref='mailto:alexey@zhokhov.com'  >  Alexey Zhokhov</a>
 *
 * Test Java 8 ISO 8601 Duration
 */
class GraphQLDurationTest extends Specification {

    @Unroll
    def "Duration parse literal #literal.value as #result"() {
        expect:
        new GraphQLDuration().getCoercing().parseLiteral(literal) == result

        where:
        literal                                     | result
        new StringValue('PT1H30M')                  | Duration.ofMinutes(90)
        new StringValue('P1DT3H')                   | Duration.ofHours(27)
    }

    @Unroll
    def "Duration returns null for invalid #literal"() {
        expect:
        new GraphQLDuration().getCoercing().parseLiteral(literal) == null

        where:
        literal                       | _
        new StringValue("not a duration") | _
    }

    @Unroll
    def "Duration serialize #value into #result (#result.class)"() {
        expect:
        new GraphQLDuration().getCoercing().serialize(value) == result

        where:
        value                 | result
        Duration.ofHours(27)  | 'PT27H'
    }

    @Unroll
    def "serialize Duration throws exception for invalid input #value"() {
        when:
        new GraphQLDuration().getCoercing().serialize(value)
        then:
        thrown(CoercingSerializeException)

        where:
        value        | _
        ''           | _
        'not a duration' | _
        '1DT3H' | _
        new Object() | _
    }

    @Unroll
    def "Duration parse #value into #result (#result.class)"() {
        expect:
        new GraphQLDuration().getCoercing().parseValue(value) == result

        where:
        value                      | result
        'PT1H30M' | Duration.ofMinutes(90)
        'P1DT3H'  | Duration.ofHours(27)
    }

    @Unroll
    def "Duration parseValue throws exception for invalid input #value"() {
        when:
        new GraphQLDuration().getCoercing().parseValue(value)
        then:
        thrown(CoercingParseValueException)

        where:
        value        | _
        ''           | _
        'not a date' | _
        '1DT3H'      | _
        new Object() | _
    }

}
