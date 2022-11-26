/*
 * Copyright 2022 Alexey Zhokhov
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
package com.tailrocks.graphql.datetime.spring.boot;

import graphql.schema.GraphQLScalarType;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @author Alexey Zhokhov
 */
@AutoConfiguration(before = {org.springframework.boot.autoconfigure.graphql.GraphQlAutoConfiguration.class})
@ConditionalOnClass({org.springframework.graphql.execution.RuntimeWiringConfigurer.class})
public class GraphQlDateTimeAutoConfiguration {

    @Bean(name = "graphqlDateTimeConfigurer")
    @ConditionalOnMissingBean(name = "graphqlDateTimeConfigurer")
    public org.springframework.graphql.execution.RuntimeWiringConfigurer graphqlDateTimeConfigurer(
            GraphQLScalarType graphQlDateScalar,
            GraphQLScalarType graphQlLocalDateScalar,
            GraphQLScalarType graphQlLocalDateTimeScalar,
            GraphQLScalarType graphQlLocalTimeScalar,
            GraphQLScalarType graphQlOffsetDateTimeScalar,
            GraphQLScalarType graphQlYearMonthScalar,
            GraphQLScalarType graphQlDurationScalar
    ) {
        return builder -> {
            builder.scalar(graphQlDateScalar);
            builder.scalar(graphQlLocalDateScalar);
            builder.scalar(graphQlLocalDateTimeScalar);
            builder.scalar(graphQlLocalTimeScalar);
            builder.scalar(graphQlOffsetDateTimeScalar);
            builder.scalar(graphQlYearMonthScalar);
            builder.scalar(graphQlDurationScalar);
        };
    }

}
