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

import java.time.LocalTime
import java.util.concurrent.TimeUnit

/**
 * @author <a href='mailto:alexey@zhokhov.com'>Alexey Zhokhov</a>
 */
class GraphQLLocalTimeTest extends Specification {

    @Unroll
    def "Date parse literal #literal.value as #result"() {
        expect:
        new GraphQLLocalTime().getCoercing().parseLiteral(literal) == result

        where:
        literal                     | result
        new StringValue('00:00:00') | LocalTime.MIDNIGHT
        new StringValue('10:15:30') | LocalTime.of(10, 15, 30)
        new StringValue('17:59:59') | LocalTime.of(17, 59, 59)
    }

    @Unroll
    def "Date returns null for invalid #literal"() {
        expect:
        new GraphQLLocalTime().getCoercing().parseLiteral(literal) == null

        where:
        literal                       | _
        new StringValue("not a time") | _
    }

    @Unroll
    def "Date serialize #value into #result (#result.class)"() {
        expect:
        new GraphQLLocalTime().getCoercing().serialize(value) == result

        where:
        value                                                              | result
        LocalTime.MIDNIGHT                                                 | '00:00:00'
        LocalTime.of(10, 15, 30)                                           | '10:15:30'
        LocalTime.of(17, 59, 59)                                           | '17:59:59'
        LocalTime.of(17, 59, 59, (int) TimeUnit.MILLISECONDS.toNanos(277)) | '17:59:59.277'
    }

    @Unroll
    def "serialize throws exception for invalid input #value"() {
        when:
        new GraphQLLocalTime().getCoercing().serialize(value)
        then:
        thrown(CoercingSerializeException)

        where:
        value        | _
        ''           | _
        'not a time' | _
        new Object() | _
    }

    @Unroll
    def "Date parse #value into #result (#result.class)"() {
        expect:
        new GraphQLLocalTime().getCoercing().parseValue(value) == result

        where:
        value          | result
        '00:00:00'     | LocalTime.MIDNIGHT
        '10:15:30'     | LocalTime.of(10, 15, 30)
        '17:59:59'     | LocalTime.of(17, 59, 59)
        '17:59:59.277' | LocalTime.of(17, 59, 59, (int) TimeUnit.MILLISECONDS.toNanos(277))
    }

    @Unroll
    def "parseValue throws exception for invalid input #value"() {
        when:
        new GraphQLLocalTime().getCoercing().parseValue(value)
        then:
        thrown(CoercingParseValueException)

        where:
        value        | _
        ''           | _
        'not a time' | _
        new Object() | _
    }

}
