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
package com.zhokhov.graphql.datetime.boot.test

import graphql.GraphQL
import graphql.schema.GraphQLSchema
import org.springframework.context.support.AbstractApplicationContext
import spock.lang.Specification

/**
 * @author Alexey Zhokhov
 */
class GraphQLDateTimeAutoConfigurationSpec extends Specification {

    AbstractApplicationContext context

    def setup() {
        context = ContextHelper.load()
    }

    def cleanup() {
        if (context) {
            context.close()
            context = null
        }
    }

    def "basic"() {
        given:
            String query = """
{
    echo {
        date
        localDate
        localDateTime
        localTime
        offsetDateTime
    }
}
"""

        when:
            this.context = ContextHelper.load()

        then:
            context.getBean(GraphQLSchema.class)

            context.getBean("graphQLDate")
            context.getBean("graphQLLocalDate")
            context.getBean("graphQLLocalDateTime")
            context.getBean("graphQLLocalTime")
            context.getBean("graphQLOffsetDateTime")

        when:
            GraphQL graphQL = GraphQL.newGraphQL(context.getBean(GraphQLSchema.class)).build()
            Map<String, Object> result = graphQL.execute(query).getData()

        then:
            result == [
                    echo: [
                            date          : '2017-07-10T06:12:46.754Z',
                            localDate     : '2017-01-01',
                            localDateTime : '2017-01-01T00:00:00Z',
                            offsetDateTime: '2017-01-01T00:00:00Z',
                            localTime     : '00:00:00'
                    ]
            ]
    }

}
