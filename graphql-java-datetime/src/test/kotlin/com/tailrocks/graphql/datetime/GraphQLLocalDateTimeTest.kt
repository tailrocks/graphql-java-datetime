/*
 * Copyright 2017 Alexey Zhokhov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tailrocks.graphql.datetime

import graphql.language.StringValue
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FreeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ISO_INSTANT
import java.util.TimeZone
import java.util.concurrent.TimeUnit.MILLISECONDS

/**
 * @author Alexey Zhokhov
 */
class GraphQLLocalDateTimeTest : FreeSpec({

    "parseLiteral -> success" - {
        listOf(
            StringValue("2017-07-09T11:54:42.277Z")
                    to LocalDateTime.of(2017, 7, 9, 11, 54, 42, MILLISECONDS.toNanos(277).toInt()),
            StringValue("2017-07-09T13:14:45.947Z")
                    to LocalDateTime.of(2017, 7, 9, 13, 14, 45, MILLISECONDS.toNanos(947).toInt()),
            StringValue("2017-07-09T11:54:42Z")
                    to LocalDateTime.of(2017, 7, 9, 11, 54, 42),
            StringValue("2017-07-09T13:14:45.947")
                    to LocalDateTime.of(2017, 7, 9, 13, 14, 45, MILLISECONDS.toNanos(947).toInt()),
            StringValue("2017-07-09T11:54:42")
                    to LocalDateTime.of(2017, 7, 9, 11, 54, 42),
            StringValue("2017-07-09")
                    to LocalDateTime.of(LocalDate.of(2017, 7, 9), LocalTime.MIDNIGHT)
        ).forEach { (literal, result) ->
            "parse literal $literal as $result (${result::class.java})" {
                GraphqlLocalDateTimeCoercing(false, ISO_INSTANT).parseLiteral(literal) shouldBe result
            }
        }
    }

    "parseLiteral -> fail" - {
        listOf(
            StringValue(""),
            StringValue("not a localdatetime"),
            Object(),
        ).forEach { literal ->
            "throws exception for the input: $literal" {
                shouldThrow<CoercingParseLiteralException> {
                    GraphqlLocalDateTimeCoercing(false, ISO_INSTANT).parseLiteral(literal)
                }
            }
        }
    }

    "serialize -> success" - {
        listOf(
            LocalDateTime.of(2017, 7, 9, 11, 54, 42, MILLISECONDS.toNanos(277).toInt())
                    to "2017-07-09T11:54:42.277Z",
            LocalDateTime.of(2017, 7, 9, 13, 14, 45, MILLISECONDS.toNanos(947).toInt())
                    to "2017-07-09T13:14:45.947Z",
            LocalDateTime.of(2017, 7, 9, 11, 54, 42)
                    to "2017-07-09T11:54:42Z",
            LocalDateTime.of(LocalDate.of(2017, 7, 9), LocalTime.MIDNIGHT)
                    to "2017-07-09T00:00:00Z"
        ).forEach { (value, result) ->
            "serialize $value (${value::class}) into $result" {
                GraphqlLocalDateTimeCoercing(false, ISO_INSTANT).serialize(value) shouldBe result
            }
        }
    }

    "serialize -> fail" - {
        listOf(
            "",
            "not a localdatetime",
            Object()
        ).forEach { value ->
            "throws exception for the input: $value" {
                shouldThrow<CoercingSerializeException> {
                    GraphqlLocalDateTimeCoercing(false, ISO_INSTANT).serialize(value)
                }
            }
        }
    }

    "parseValue -> success" - {
        listOf(
            "2017-07-09T11:54:42.277Z"
                    to LocalDateTime.of(2017, 7, 9, 11, 54, 42, MILLISECONDS.toNanos(277).toInt()),
            "2017-07-09T13:14:45.947Z"
                    to LocalDateTime.of(2017, 7, 9, 13, 14, 45, MILLISECONDS.toNanos(947).toInt()),
            "2017-07-09T11:54:42Z"
                    to LocalDateTime.of(2017, 7, 9, 11, 54, 42),
            "2017-07-09T13:14:45.947"
                    to LocalDateTime.of(2017, 7, 9, 13, 14, 45, MILLISECONDS.toNanos(947).toInt()),
            "2017-07-09T11:54:42"
                    to LocalDateTime.of(2017, 7, 9, 11, 54, 42),
            "2017-07-09"
                    to LocalDateTime.of(LocalDate.of(2017, 7, 9), LocalTime.MIDNIGHT)
        ).forEach { (value, result) ->
            "parse $value into $result (${result::class.java})" {
                GraphqlLocalDateTimeCoercing(false, ISO_INSTANT).parseValue(value) shouldBe result
            }
        }
    }

    "parseValue -> fail" - {
        listOf(
            "",
            "not a localdatetime",
            Object()
        ).forEach { value ->
            "throws exception for the input: $value" {
                shouldThrow<CoercingParseValueException> {
                    GraphqlLocalDateTimeCoercing(false, ISO_INSTANT).parseValue(value)
                }
            }
        }
    }

    "zone conversion" - {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.ofHours(1)))

        "serialize -> success" - {
            listOf(
                LocalDateTime.of(2017, 7, 9, 11, 54, 42, MILLISECONDS.toNanos(277).toInt())
                        to "2017-07-09T10:54:42.277Z",
                LocalDateTime.of(2017, 7, 9, 13, 14, 45, MILLISECONDS.toNanos(947).toInt())
                        to "2017-07-09T12:14:45.947Z",
                LocalDateTime.of(2017, 7, 9, 11, 54, 42)
                        to "2017-07-09T10:54:42Z",
                LocalDateTime.of(LocalDate.of(2017, 7, 9), LocalTime.MIDNIGHT.plusHours(1))
                        to "2017-07-09T00:00:00Z"
            ).forEach { (value, result) ->
                "serialize $value (${value::class.java}) into $result using zone conversion" {
                    GraphqlLocalDateTimeCoercing(true, ISO_INSTANT).serialize(value) shouldBe result
                }
            }
        }

        "parseValue -> success" - {
            listOf(
                "2017-07-09T10:54:42.277Z"
                        to LocalDateTime.of(2017, 7, 9, 11, 54, 42, MILLISECONDS.toNanos(277).toInt()),
                "2017-07-09T12:14:45.947Z"
                        to LocalDateTime.of(2017, 7, 9, 13, 14, 45, MILLISECONDS.toNanos(947).toInt()),
                "2017-07-09T10:54:42Z"
                        to LocalDateTime.of(2017, 7, 9, 11, 54, 42),
                "2017-07-09"
                        to LocalDateTime.of(LocalDate.of(2017, 7, 9), LocalTime.MIDNIGHT)
            ).forEach { (value, result) ->
                "parse $value into $result (${result::class.java}) using zone conversion" {
                    GraphqlLocalDateTimeCoercing(true, ISO_INSTANT).parseValue(value) shouldBe result
                }
            }
        }
    }

    "custom formatter" - {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

        "parseValue -> success" - {
            listOf(
                "1993-02-09T13:15:59"
                        to LocalDateTime.of(1993, 2, 9, 13, 15, 59)
            ).forEach { (value, result) ->
                "parse $value into $result (${result::class.java}) with custom formatter" {
                    GraphqlLocalDateTimeCoercing(false, formatter).parseValue(value) shouldBe result
                }
            }
        }

        "serialize -> success" - {
            listOf(
                LocalDateTime.of(1993, 2, 9, 13, 15, 59)
                        to "1993-02-09T13:15:59"
            ).forEach { (value, result) ->
                "serialize $value (${value::class.java}) into $result with custom formatting" {
                    GraphqlLocalDateTimeCoercing(false, formatter).serialize(value) shouldBe result
                }
            }
        }
    }

}) {
    init {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC))
    }
}
