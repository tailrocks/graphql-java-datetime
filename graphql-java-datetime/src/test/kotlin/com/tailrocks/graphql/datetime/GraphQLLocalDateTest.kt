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

import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

import java.time.ZoneOffset.UTC
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
import java.util.*

/**
 * @author Alexey Zhokhov
 */
class GraphQLLocalDateTest : FreeSpec({

    "parseLiteral -> success" - {
        listOf(
            StringValue("2017-07-09") to LocalDate.of(2017, 7, 9)
        ).forEach { (literal, result) ->
            "parse literal ${literal.value} as $result" {
                GraphqlLocalDateCoercing(false, ISO_LOCAL_DATE).parseLiteral(literal) shouldBe result
            }
        }
    }

    "parseLiteral -> fail" - {
        listOf(
            StringValue(""),
            StringValue("not a localdate"),
        ).forEach { literal ->
            "throws exception for invalid $literal" {
                shouldThrow<CoercingParseLiteralException> {
                    GraphqlLocalDateCoercing(false, ISO_LOCAL_DATE).parseLiteral(literal)
                }
            }
        }
    }

    "serialize -> success" - {
        listOf(
            LocalDate.of(2017, 7, 9) to "2017-07-09"
        ).forEach { (value, result) ->
            "serialize $value into $result (${result::class.java})" {
                GraphqlLocalDateCoercing(false, ISO_LOCAL_DATE).serialize(value) shouldBe result
            }
        }
    }

    "serialize -> fail" - {
        listOf(
            "",
            "not a localdate",
            Object()
        ).forEach { value ->
            "throws exception for invalid input $value" {
                shouldThrow<CoercingSerializeException> {
                    GraphqlLocalDateCoercing(false, ISO_LOCAL_DATE).serialize(value)
                }

            }
        }
    }

    "parseValue -> success" - {
        listOf(
            "2017-07-09" to LocalDate.of(2017, 7, 9)
        ).forEach { (value, result) ->
            "parse $value into $result (${result::class.java})" {
                GraphqlLocalDateCoercing(false, ISO_LOCAL_DATE).parseValue(value) shouldBe result
            }
        }
    }

    "parseValue -> fail" - {
        listOf(
            "",
            "not a date",
            Object()
        ).forEach { value ->
            "throws exception for invalid $value" {
                shouldThrow<CoercingParseValueException> {
                    GraphqlLocalDateCoercing(false, ISO_LOCAL_DATE).parseValue(value)
                }
            }
        }
    }

    "zone conversion" - {
        TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.ofHours(2)))

        "parseValue -> success" - {
            listOf(
                "2019-03-01" to LocalDate.of(2019, 3, 1),
                "2019-03-01T22:00:00Z" to LocalDate.of(2019, 3, 2)
            ).forEach { (value, result) ->
                "parse $value into $result (${result::class.java}) using zone conversion" {
                    GraphqlLocalDateCoercing(true, ISO_LOCAL_DATE).parseValue(value) shouldBe result
                }
            }
        }
    }

    "custom formatter" - {
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")

        "parseValue -> success" - {
            listOf(
                "02/09/1993" to LocalDate.of(1993, 2, 9)
            ).forEach { (value, result) ->
                "parse $value into $result (${result::class.java}) with custom formatter" {
                    GraphqlLocalDateCoercing(false, formatter).parseValue(value) shouldBe result
                }
            }
        }

        "serialize -> success" - {
            listOf(
                LocalDate.of(2020, 7, 6) to "07/06/2020"
            ).forEach { (value, result) ->
                "serialize $value into $result (${result::class.java}) with custom formatting" {
                    GraphqlLocalDateCoercing(false, formatter).serialize(value) shouldBe result
                }
            }
        }
    }

}) {
    init {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC))
    }
}
