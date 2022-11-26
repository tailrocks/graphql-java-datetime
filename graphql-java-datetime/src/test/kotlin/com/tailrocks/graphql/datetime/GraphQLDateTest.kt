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
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.time.ZoneOffset.UTC
import java.util.*

/**
 * @author Alexey Zhokhov
 */
class GraphQLDateTest : FreeSpec({

    "parseLiteral -> success" - {
        listOf(
            StringValue("2017-07-09T11:54:42.277Z")
                    to createDate(2017, 7, 9, 11, 54, 42, 277),
            StringValue("2017-07-09T13:14:45.947Z")
                    to createDate(2017, 7, 9, 13, 14, 45, 947),
            StringValue("2017-07-09T11:54:42Z")
                    to createDate(2017, 7, 9, 11, 54, 42),
            StringValue("2017-07-09")
                    to createDate(2017, 7, 9)
        ).forEach { (literal, result) ->
            "parse literal ${literal.value} as $result" {
                GraphqlDateCoercing().parseLiteral(literal) shouldBe result
            }
        }
    }

    "parseLiteral -> fail" - {
        listOf(
            StringValue(""),
            StringValue("not a date")
        ).forEach { literal ->
            "throws exception for input: $literal" {
                shouldThrow<CoercingParseLiteralException> {
                    GraphqlDateCoercing().parseLiteral(literal)
                }
            }
        }
    }

    /*

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

 */

    "parseValue -> success" - {
        listOf(
            "2017-07-09T11:54:42.277Z"
                    to createDate(2017, 7, 9, 11, 54, 42, 277),
            "2017-07-09T13:14:45.947Z"
                    to createDate(2017, 7, 9, 13, 14, 45, 947),
            "2017-07-09T11:54:42Z"
                    to createDate(2017, 7, 9, 11, 54, 42),
            "2017-07-09"
                    to createDate(2017, 7, 9)
        ).forEach { (value, result) ->
            "parse $value into $result ($result.class)" {
                GraphqlDateCoercing().parseValue(value) shouldBe result
            }
        }
    }

    "parseValue -> fail" - {
        listOf(
            "",
            "not a date",
            Object()
        ).forEach { value ->
            "throws exception for invalid input: $value" {
                shouldThrow<CoercingParseValueException> {
                    GraphqlDateCoercing().parseValue(value)
                }
            }
        }
    }

}) {
    init {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC))
    }
}
