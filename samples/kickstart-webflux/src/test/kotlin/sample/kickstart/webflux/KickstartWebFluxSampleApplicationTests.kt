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
package sample.kickstart.webflux

import graphql.GraphQL
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLSchema
import io.kotest.core.spec.style.FreeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import org.apache.commons.text.StringEscapeUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.ApplicationContext
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KickstartWebFluxSampleApplicationTests : FreeSpec() {

    override fun extensions() = listOf(SpringExtension)

    data class GraphQLResponse(val data: ResponseData?)

    data class ResponseData(
        val emperors: List<Emperor>,
        val now: String,
        val yesterday: String,
        val tomorrowMidnight: String,
        val noonTime: String,
        val springFirstRelease: String
    )

    data class Emperor(val givenName: String, val title: String, val reignStart: String, val reignStop: String)

    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Autowired
    lateinit var applicationContext: ApplicationContext

    init {
        "check beans" {
            applicationContext.getBean("graphQlDateScalar").shouldBeInstanceOf<GraphQLScalarType>()
            applicationContext.getBean("graphQlLocalDateScalar").shouldBeInstanceOf<GraphQLScalarType>()
            applicationContext.getBean("graphQlLocalDateTimeScalar").shouldBeInstanceOf<GraphQLScalarType>()
            applicationContext.getBean("graphQlLocalTimeScalar").shouldBeInstanceOf<GraphQLScalarType>()
            applicationContext.getBean("graphQlOffsetDateTimeScalar").shouldBeInstanceOf<GraphQLScalarType>()
            applicationContext.getBean("graphQlYearMonthScalar").shouldBeInstanceOf<GraphQLScalarType>()
            applicationContext.getBean("graphQlDurationScalar").shouldBeInstanceOf<GraphQLScalarType>()
        }

        "check HTTP response" {
            // given:
            val graphQlQuery = """
{
  emperors {
    givenName
    title
    reignStart
    reignStop
  }
  now
  yesterday
  tomorrowMidnight
  noonTime
  springFirstRelease
}
""".trim()

            // when:
            val json = """
{
    "query": "${StringEscapeUtils.escapeJson(graphQlQuery)}"
}
"""

            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
            }

            val entity = HttpEntity(json, headers)

            val response = restTemplate.postForEntity("/graphql", entity, GraphQLResponse::class.java)

            // then:
            response.shouldNotBeNull()
            response.statusCode shouldBe HttpStatus.OK
            response.body.shouldNotBeNull().apply {
                data.shouldNotBeNull().apply {
                    emperors.shouldNotBeNull().apply {
                        size shouldBe 5

                        get(0).apply {
                            givenName shouldBe "旻寧"
                            title shouldBe "Daoguang Emperor"
                            reignStart shouldBe "1820-10-03"
                            reignStop shouldBe "1850-02-25"
                        }

                        get(1).apply {
                            givenName shouldBe "奕詝"
                            title shouldBe "Xianfeng Emperor"
                            reignStart shouldBe "1850-03-09"
                            reignStop shouldBe "1861-08-22"
                        }

                        get(2).apply {
                            givenName shouldBe "載淳"
                            title shouldBe "Tongzhi Emperor"
                            reignStart shouldBe "1861-11-11"
                            reignStop shouldBe "1875-01-12"
                        }

                        get(3).apply {
                            givenName shouldBe "載湉"
                            title shouldBe "Guangxu Emperor"
                            reignStart shouldBe "1875-02-25"
                            reignStop shouldBe "1908-11-14"
                        }

                        get(4).apply {
                            givenName shouldBe "溥儀"
                            title shouldBe "Xuantong Emperor"
                            reignStart shouldBe "1908-11-14"
                            reignStop shouldBe "1912-02-12"
                        }
                    }
                }

                data.now.shouldNotBeNull().also {
                    LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME)
                }

                data.yesterday.shouldNotBeNull().also {
                    it shouldBe LocalDate.now().minusDays(1).toString()
                }

                data.tomorrowMidnight.shouldNotBeNull().also {
                    it shouldBe DateTimeFormatter.ISO_DATE_TIME.format(
                        LocalDate.now().plusDays(1).atStartOfDay().atZone(ZoneOffset.UTC)
                    )
                }

                data.noonTime.shouldNotBeNull().also {
                    it shouldBe "12:00:00"
                }

                data.springFirstRelease.shouldNotBeNull().also {
                    it shouldBe "2002-10"
                }
            }
        }

        "check GraphQL engine execution" {
            // given:
            val query = """
{
    ping {
        date
        localDate
        localDateTime
        localTime
        offsetDateTime
    }
}
"""

            // when:
            val graphQL = GraphQL.newGraphQL(applicationContext.getBean(GraphQLSchema::class.java)).build()

            val executionResult = graphQL.execute(query)

            // then:
            executionResult.errors.isEmpty().shouldBeTrue()
            executionResult.isDataPresent.shouldBeTrue()

            executionResult.getData<Map<String, Map<String, String>>>().apply {
                get("ping").shouldNotBeNull().apply {
                    get("date") shouldBe "2017-07-10T06:12:46.754Z"
                    get("localDate") shouldBe "2017-01-01"
                    get("localDateTime") shouldBe "2017-01-01T00:00:00Z"
                    get("offsetDateTime") shouldBe "2017-01-01T00:00:00Z"
                    get("localTime") shouldBe "00:00:00"
                }
            }
        }
    }

}
