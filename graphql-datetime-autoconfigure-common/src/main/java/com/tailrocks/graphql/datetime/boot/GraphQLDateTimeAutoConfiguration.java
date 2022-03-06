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
package com.zhokhov.graphql.datetime.boot;

import com.zhokhov.graphql.datetime.DateScalar;
import com.zhokhov.graphql.datetime.DurationScalar;
import com.zhokhov.graphql.datetime.LocalDateScalar;
import com.zhokhov.graphql.datetime.LocalDateTimeScalar;
import com.zhokhov.graphql.datetime.LocalTimeScalar;
import com.zhokhov.graphql.datetime.OffsetDateTimeScalar;
import com.zhokhov.graphql.datetime.YearMonthScalar;
import graphql.kickstart.autoconfigure.tools.GraphQLJavaToolsAutoConfiguration;
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
@AutoConfigureBefore({GraphQLJavaToolsAutoConfiguration.class})
@EnableConfigurationProperties(GraphQLDateTimeProperties.class)
public class GraphQLDateTimeAutoConfiguration {

    @Bean(name = "graphQLDate")
    @ConditionalOnMissingBean(name = "graphQLDate")
    public GraphQLScalarType graphQLDate(GraphQLDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getDate().getScalarName();
        return DateScalar.create(name);
    }

    @Bean(name = "graphQLLocalDate")
    @ConditionalOnMissingBean(name = "graphQLLocalDate")
    public GraphQLScalarType graphQLLocalDate(GraphQLDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getLocalDate().getScalarName();
        final String format = configurationProperties.getLocalDate().getFormat();
        return LocalDateScalar.create(
                name,
                configurationProperties.isZoneConversionEnabled(),
                format != null ? DateTimeFormatter.ofPattern(format) : null
        );
    }

    @Bean(name = "graphQLLocalDateTime")
    @ConditionalOnMissingBean(name = "graphQLLocalDateTime")
    public GraphQLScalarType graphQLLocalDateTime(GraphQLDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getLocalDateTime().getScalarName();
        final String format = configurationProperties.getLocalDateTime().getFormat();
        return LocalDateTimeScalar.create(
                name,
                configurationProperties.isZoneConversionEnabled(),
                format != null ? DateTimeFormatter.ofPattern(format) : null
        );
    }

    @Bean(name = "graphQLLocalTime")
    @ConditionalOnMissingBean(name = "graphQLLocalTime")
    public GraphQLScalarType graphQLLocalTime(GraphQLDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getLocalTime().getScalarName();
        return LocalTimeScalar.create(name);
    }

    @Bean(name = "graphQLOffsetDateTime")
    @ConditionalOnMissingBean(name = "graphQLOffsetDateTime")
    public GraphQLScalarType graphQLOffsetDateTime(GraphQLDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getOffsetDateTime().getScalarName();
        return OffsetDateTimeScalar.create(name);
    }

    @Bean(name = "graphQLYearMonth")
    @ConditionalOnMissingBean(name = "graphQLYearMonth")
    public GraphQLScalarType graphQLYearMonth(GraphQLDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getYearMonth().getScalarName();
        return YearMonthScalar.create(name);
    }

    @Bean(name = "graphQLDuration")
    @ConditionalOnMissingBean(name = "graphQLDuration")
    public GraphQLScalarType graphQLDuration(GraphQLDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getDuration().getScalarName();
        return DurationScalar.create(name);
    }

}
