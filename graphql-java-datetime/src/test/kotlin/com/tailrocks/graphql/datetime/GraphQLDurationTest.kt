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

import java.time.Duration

import java.time.ZoneOffset.UTC
import java.util.*

/**
 * @author Alexey Zhokhov
 *
 * Test Java 8 ISO 8601 Duration
 */
class GraphQLDurationTest : FreeSpec({

    "parseLiteral -> success" - {
        listOf(
            StringValue("PT1H30M") to Duration.ofMinutes(90),
            StringValue("P1DT3H") to Duration.ofHours(27)
        ).forEach { (literal, result) ->
            "parse literal ${literal.value} as $result" {
                GraphqlDurationCoercing().parseLiteral(literal) shouldBe result
            }
        }
    }

    "parseLiteral -> fail" - {
        listOf(
            StringValue(""),
            StringValue("not a duration")
        ).forEach { literal ->
            "throws exception for invalid $literal" {
                shouldThrow<CoercingParseLiteralException> {
                    GraphqlDurationCoercing().parseLiteral(literal)
                }
            }
        }
    }

    "serialize -> success" - {
        listOf(
            Duration.ofHours(27) to "PT27H"
        ).forEach { (value, result) ->
            "serialize $value into $result (${result::class.java})" {
                GraphqlDurationCoercing().serialize(value) shouldBe result
            }
        }
    }

    "serialize -> fail" - {
        listOf(
            "",
            "not a duration",
            "1DT3H",
            Object()
        ).forEach { value ->
            "throws exception for invalid input: $value" {
                shouldThrow<CoercingSerializeException> {
                    GraphqlDurationCoercing().serialize(value)
                }
            }
        }
    }

    "parseValue -> success" - {
        listOf(
            "PT1H30M" to Duration.ofMinutes(90),
            "P1DT3H" to Duration.ofHours(27)
        ).forEach { (value, result) ->
            "parse $value into $result (${result::class.java})" {
                GraphqlDurationCoercing().parseValue(value) shouldBe result
            }
        }
    }

    "parseValue -> fail" - {
        listOf(
            "",
            "not a date",
            "1DT3H",
            Object()
        ).forEach { value ->
            "throws exception for invalid input: $value" {
                shouldThrow<CoercingParseValueException> {
                    GraphqlDurationCoercing().parseValue(value)
                }
            }
        }
    }

}) {
    init {
        TimeZone.setDefault(TimeZone.getTimeZone(UTC))
    }
}
