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

import com.tailrocks.graphql.datetime.DateTimeHelper.createDate
import graphql.language.StringValue
import io.kotest.core.spec.style.FunSpec
import io.kotest.datatest.withData
import io.kotest.matchers.shouldBe
import java.time.ZoneOffset.UTC
import java.util.*

/**
 * @author Alexey Zhokhov
 */
class GraphQLDateTest : FunSpec({

    context("parse literal") {
        data class Item(val literal: StringValue, val result: Date)

        withData(
            nameFn = { "${it.literal} -> ${it.result}" },
            listOf(
                Item(
                    StringValue("2017-07-09T11:54:42.277Z"),
                    createDate(2017, 7, 9, 11, 54, 42, 277)
                ),
                Item(
                    StringValue("2017-07-09T13:14:45.947Z"),
                    createDate(2017, 7, 9, 13, 14, 45, 947),
                ),
                Item(
                    StringValue("2017-07-09T11:54:42Z"),
                    createDate(2017, 7, 9, 11, 54, 42),
                ),
                Item(
                    StringValue("2017-07-09"),
                    createDate(2017, 7, 9)
                )
            )
        ) { (literal: StringValue, result: Date) ->
            GraphqlDateCoercing().parseLiteral(literal) shouldBe result
        }
    }

    /*
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
 */

}) {

    init {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC))
    }

}
