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
package com.tailrocks.graphql.datetime.spring.boot;

import com.tailrocks.graphql.datetime.DateScalar;
import com.tailrocks.graphql.datetime.DurationScalar;
import com.tailrocks.graphql.datetime.LocalDateScalar;
import com.tailrocks.graphql.datetime.LocalDateTimeScalar;
import com.tailrocks.graphql.datetime.LocalTimeScalar;
import com.tailrocks.graphql.datetime.OffsetDateTimeScalar;
import com.tailrocks.graphql.datetime.YearMonthScalar;
import graphql.schema.GraphQLScalarType;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

/**
 * @author Alexey Zhokhov
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore(name = {"graphql.kickstart.autoconfigure.tools.GraphQLJavaToolsAutoConfiguration"})
@EnableConfigurationProperties(GraphQlDateTimeProperties.class)
public class GraphQlDateTimeScalarsAutoConfiguration {

    @Bean(name = "graphQlDateScalar")
    @ConditionalOnMissingBean(name = "graphQlDateScalar")
    public GraphQLScalarType graphQlDateScalar(GraphQlDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getDate().getScalarName();
        return DateScalar.create(name);
    }

    @Bean(name = "graphQlLocalDateScalar")
    @ConditionalOnMissingBean(name = "graphQlLocalDateScalar")
    public GraphQLScalarType graphQlLocalDateScalar(GraphQlDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getLocalDate().getScalarName();
        final String format = configurationProperties.getLocalDate().getFormat();
        return LocalDateScalar.create(
                name,
                configurationProperties.isZoneConversionEnabled(),
                format != null ? DateTimeFormatter.ofPattern(format) : null
        );
    }

    @Bean(name = "graphQlLocalDateTimeScalar")
    @ConditionalOnMissingBean(name = "graphQlLocalDateTimeScalar")
    public GraphQLScalarType graphQlLocalDateTimeScalar(GraphQlDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getLocalDateTime().getScalarName();
        final String format = configurationProperties.getLocalDateTime().getFormat();
        return LocalDateTimeScalar.create(
                name,
                configurationProperties.isZoneConversionEnabled(),
                format != null ? DateTimeFormatter.ofPattern(format) : null
        );
    }

    @Bean(name = "graphQlLocalTimeScalar")
    @ConditionalOnMissingBean(name = "graphQlLocalTimeScalar")
    public GraphQLScalarType graphQlLocalTimeScalar(GraphQlDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getLocalTime().getScalarName();
        return LocalTimeScalar.create(name);
    }

    @Bean(name = "graphQlOffsetDateTimeScalar")
    @ConditionalOnMissingBean(name = "graphQlOffsetDateTimeScalar")
    public GraphQLScalarType graphQlOffsetDateTimeScalar(GraphQlDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getOffsetDateTime().getScalarName();
        return OffsetDateTimeScalar.create(name);
    }

    @Bean(name = "graphQlYearMonthScalar")
    @ConditionalOnMissingBean(name = "graphQlYearMonthScalar")
    public GraphQLScalarType graphQlYearMonthScalar(GraphQlDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getYearMonth().getScalarName();
        return YearMonthScalar.create(name);
    }

    @Bean(name = "graphQlDurationScalar")
    @ConditionalOnMissingBean(name = "graphQlDurationScalar")
    public GraphQLScalarType graphQlDurationScalar(GraphQlDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getDuration().getScalarName();
        return DurationScalar.create(name);
    }

}
