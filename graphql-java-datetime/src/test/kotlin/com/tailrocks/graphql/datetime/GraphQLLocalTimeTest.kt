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
import io.kotest.matchers.shouldBe
import java.time.LocalTime
import java.time.ZoneOffset.UTC
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author Alexey Zhokhov
 */
class GraphQLLocalTimeTest : FreeSpec({

    "parseLiteral -> success" - {
        listOf(
            StringValue("00:00:00") to LocalTime.MIDNIGHT,
            StringValue("10:15:30") to LocalTime.of(10, 15, 30),
            StringValue("17:59:59") to LocalTime.of(17, 59, 59),
        ).forEach { (literal, result) ->
            "parse literal $literal as $result (${result::class.java})" {
                GraphqlLocalTimeCoercing().parseLiteral(literal) shouldBe result
            }
        }
    }

    "parseLiteral -> fail" - {
        listOf(
            StringValue(""),
            StringValue("not a localtime"),
            Object(),
        ).forEach { literal ->
            "throws exception for the input: $literal" {
                shouldThrow<CoercingParseLiteralException> {
                    GraphqlLocalTimeCoercing().parseLiteral(literal)
                }
            }
        }
    }

    "serialize -> success" - {
        listOf(
            LocalTime.MIDNIGHT to "00:00:00",
            LocalTime.of(10, 15, 30) to "10:15:30",
            LocalTime.of(17, 59, 59) to "17:59:59",
            LocalTime.of(17, 59, 59, TimeUnit.MILLISECONDS.toNanos(277).toInt()) to "17:59:59.277",
        ).forEach { (value, result) ->
            "serialize $value (${value::class}) into $result" {
                GraphqlLocalTimeCoercing().serialize(value) shouldBe result
            }
        }
    }

    "serialize -> fail" - {
        listOf(
            "",
            "not a localtime",
            Object()
        ).forEach { value ->
            "throws exception for the input: $value" {
                shouldThrow<CoercingSerializeException> {
                    GraphqlLocalTimeCoercing().serialize(value)
                }
            }
        }
    }

    "parseValue -> success" - {
        listOf(
            "00:00:00" to LocalTime.MIDNIGHT,
            "10:15:30" to LocalTime.of(10, 15, 30),
            "17:59:59" to LocalTime.of(17, 59, 59),
            "17:59:59.277" to LocalTime.of(17, 59, 59, TimeUnit.MILLISECONDS.toNanos(277).toInt())
        ).forEach { (value, result) ->
            "parse $value into $result (${result::class.java})" {
                GraphqlLocalTimeCoercing().parseValue(value) shouldBe result
            }
        }
    }

    "parseValue -> fail" - {
        listOf(
            "",
            "not a localtime",
            Object()
        ).forEach { value ->
            "throws exception for the input: $value" {
                shouldThrow<CoercingParseValueException> {
                    GraphqlLocalTimeCoercing().parseValue(value)
                }
            }
        }
    }

}) {
    init {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC))
    }
}
