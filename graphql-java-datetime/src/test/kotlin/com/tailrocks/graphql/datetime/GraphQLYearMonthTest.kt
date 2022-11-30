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
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

import java.time.YearMonth

import java.time.ZoneOffset.UTC
import java.util.*

/**
 * @author Alexey Zhokhov
 */
class GraphQLYearMonthTest : FreeSpec({

    "parseLiteral -> success" - {
        listOf(
            StringValue("2017-07-09T11:54:42.277Z") to YearMonth.of(2017, 7),
            StringValue("2017-07-09T13:14:45.947Z") to YearMonth.of(2017, 7),
            StringValue("2017-07-09T11:54:42Z") to YearMonth.of(2017, 7),
            StringValue("2017-07-09") to YearMonth.of(2017, 7)
        ).forEach { (literal, result) ->
            "parse literal ${literal.value} as $result" {
                GraphqlYearMonthCoercing().parseLiteral(literal) shouldBe result
            }
        }
    }

    "parseLiteral -> fail" - {
        listOf(
            StringValue(""),
            StringValue("not a date")
        ).forEach { literal ->
            "throws exception for invalid $literal" {
                shouldThrow<CoercingParseLiteralException> {
                    GraphqlYearMonthCoercing().parseLiteral(literal)
                }
            }
        }
    }

    "serialize -> success" - {
        listOf(
            YearMonth.of(2017, 7) to "2017-07"
        ).forEach { (value, result) ->
            "serialize $value into $result (${result::class.java})" {
                GraphqlYearMonthCoercing().serialize(value) shouldBe result
            }
        }
    }

    "serialize -> fail" - {
        listOf(
            "",
            "not a date",
            Object()
        ).forEach { value ->
            "throws exception for invalid input $value" {
                shouldThrow<CoercingSerializeException> {
                    GraphqlYearMonthCoercing().serialize(value)
                }
            }
        }
    }

    "parseValue -> success" - {
        listOf(
            "2020-07-09T11:54:42.277Z" to YearMonth.of(2020, 7),
            "2020-07-09T13:14:45.947Z" to YearMonth.of(2020, 7),
            "2020-1" to YearMonth.of(2020, 1),
            "2020-11" to YearMonth.of(2020, 11),
        ).forEach { (value, result) ->
            "parse $value into $result (${result::class.java})" {
                GraphqlYearMonthCoercing().parseValue(value) shouldBe result
            }
        }
    }

    "parseValue -> fail" - {
        listOf(
            "",
            "not a date",
            Object(),
        ).forEach { value ->
            "throws exception for invalid input $value" {
                shouldThrow<CoercingParseValueException> {
                    GraphqlYearMonthCoercing().parseValue(value)
                }
            }
        }
    }

}) {
    init {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC))
    }
}
