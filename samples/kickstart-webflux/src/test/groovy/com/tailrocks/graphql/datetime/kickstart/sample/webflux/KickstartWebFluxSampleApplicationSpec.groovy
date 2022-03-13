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
package com.tailrocks.graphql.datetime.kickstart.sample.webflux

import graphql.ExecutionResult
import graphql.GraphQL
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLSchema
import org.apache.commons.text.StringEscapeUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

/**
 * @author Alexey Zhokhov
 */
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KickstartWebFluxSampleApplicationSpec extends Specification {

    @Autowired ApplicationContext applicationContext
    @Autowired TestRestTemplate restTemplate

    def "check beans"() {
        expect:
            applicationContext.getBean("graphQlDateScalar") instanceof GraphQLScalarType
            applicationContext.getBean("graphQlLocalDateScalar") instanceof GraphQLScalarType
            applicationContext.getBean("graphQlLocalDateTimeScalar") instanceof GraphQLScalarType
            applicationContext.getBean("graphQlLocalTimeScalar") instanceof GraphQLScalarType
            applicationContext.getBean("graphQlOffsetDateTimeScalar") instanceof GraphQLScalarType
            applicationContext.getBean("graphQlYearMonthScalar") instanceof GraphQLScalarType
            applicationContext.getBean("graphQlDurationScalar") instanceof GraphQLScalarType
    }

    def "ping"() {
        given:
            String query = """
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

        when:
            GraphQL graphQL = GraphQL.newGraphQL(applicationContext.getBean(GraphQLSchema.class)).build()
            ExecutionResult executionResult = graphQL.execute(query)
            Map<String, Object> result = executionResult.getData()

        then:
            executionResult.errors.isEmpty()

            result.ping
            result.ping.date == '2017-07-10T06:12:46.754Z'
            result.ping.localDate == '2017-01-01'
            result.ping.localDateTime == '2017-01-01T00:00:00Z'
            result.ping.offsetDateTime == '2017-01-01T00:00:00Z'
            result.ping.localTime == '00:00:00'
    }

    def "ping REST"() {
        given:
            String query = """
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

        when:
            String json = """
{
    "query": "${StringEscapeUtils.escapeJson(query)}"
}
"""

            HttpHeaders headers = new HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_JSON)

            HttpEntity entity = new HttpEntity(json, headers)

            ResponseEntity<Map> response = restTemplate.postForEntity('/graphql', entity, Map.class)

        then:
            response.statusCode == HttpStatus.OK

            response.body
            response.body == [
                    data: [
                            ping: [
                                    date          : '2017-07-10T06:12:46.754Z',
                                    localDate     : '2017-01-01',
                                    localDateTime : '2017-01-01T00:00:00Z',
                                    offsetDateTime: '2017-01-01T00:00:00Z',
                                    localTime     : '00:00:00'
                            ]
                    ]
            ]
    }

}
