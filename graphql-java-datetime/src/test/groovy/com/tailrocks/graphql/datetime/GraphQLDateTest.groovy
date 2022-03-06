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

import static com.zhokhov.graphql.datetime.DateTimeHelper.createDate
import static java.time.ZoneOffset.UTC

/**
 * @author Alexey Zhokhov
 */
class GraphQLDateTest extends Specification {

    def setup() {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC))
    }

    @Unroll
    def "Date parse literal #literal.value as #result"() {
        expect:
            new GraphqlDateCoercing().parseLiteral(literal) == result

        where:
            literal                                     | result
            new StringValue('2017-07-09T11:54:42.277Z') | createDate(2017, 7, 9, 11, 54, 42, 277)
            new StringValue('2017-07-09T13:14:45.947Z') | createDate(2017, 7, 9, 13, 14, 45, 947)
            new StringValue('2017-07-09T11:54:42Z')     | createDate(2017, 7, 9, 11, 54, 42)
            new StringValue('2017-07-09')               | createDate(2017, 7, 9)
    }

    @Unroll
    def "Date parseLiteral throws exception for invalid #literal"() {
        when:
            new GraphqlDateCoercing().parseLiteral(literal)

        then:
            thrown(CoercingParseLiteralException)

        where:
            literal                       | _
            new StringValue('')           | _
            new StringValue('not a date') | _
    }

    @Unroll
    def "Date serialize #value into #result (#result.class)"() {
        expect:
            new GraphqlDateCoercing().serialize(value) == result

        where:
            value                                   | result
            createDate(2017, 7, 9, 11, 54, 42, 277) | '2017-07-09T11:54:42.277Z'
            createDate(2017, 7, 9, 13, 14, 45, 947) | '2017-07-09T13:14:45.947Z'
            createDate(2017, 7, 9, 11, 54, 42)      | '2017-07-09T11:54:42Z'
            createDate(2017, 7, 9)                  | '2017-07-09T00:00:00Z'
    }

    @Unroll
    def "serialize throws exception for invalid input #value"() {
        when:
            new GraphqlDateCoercing().serialize(value)
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
            new GraphqlDateCoercing().parseValue(value) == result

        where:
            value                      | result
            '2017-07-09T11:54:42.277Z' | createDate(2017, 7, 9, 11, 54, 42, 277)
            '2017-07-09T13:14:45.947Z' | createDate(2017, 7, 9, 13, 14, 45, 947)
            '2017-07-09T11:54:42Z'     | createDate(2017, 7, 9, 11, 54, 42)
            '2017-07-09'               | createDate(2017, 7, 9)
    }

    @Unroll
    def "parseValue throws exception for invalid input #value"() {
        when:
            new GraphqlDateCoercing().parseValue(value)
        then:
            thrown(CoercingParseValueException)

        where:
            value        | _
            ''           | _
            'not a date' | _
            new Object() | _
    }

}
