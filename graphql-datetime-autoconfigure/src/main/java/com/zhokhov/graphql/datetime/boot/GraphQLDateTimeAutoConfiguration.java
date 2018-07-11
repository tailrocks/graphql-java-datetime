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
package com.zhokhov.graphql.datetime.boot;

import com.oembedler.moon.graphql.boot.GraphQLJavaToolsAutoConfiguration;
import com.zhokhov.graphql.datetime.GraphQLDate;
import com.zhokhov.graphql.datetime.GraphQLLocalDate;
import com.zhokhov.graphql.datetime.GraphQLLocalDateTime;
import com.zhokhov.graphql.datetime.GraphQLLocalTime;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href='mailto:alexey@zhokhov.com'>Alexey Zhokhov</a>
 */
@Configuration
@AutoConfigureBefore({GraphQLJavaToolsAutoConfiguration.class})
public class GraphQLDateTimeAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GraphQLDate graphQLDate(
            GraphQLDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getDate().getScalarName();
        if (name == null) {
            return new GraphQLDate();
        } else {
            return new GraphQLDate(name);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public GraphQLLocalDate graphQLLocalDate(
            GraphQLDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getLocalDate().getScalarName();
        if (name == null) {
            return new GraphQLLocalDate();
        } else {
            return new GraphQLLocalDate(name);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public GraphQLLocalDateTime graphQLLocalDateTime(
            GraphQLDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getLocalDateTime().getScalarName();
        if (name == null) {
            return new GraphQLLocalDateTime();
        } else {
            return new GraphQLLocalDateTime(name);
        }
    }

    @Bean
    @ConditionalOnMissingBean
    public GraphQLLocalTime graphQLLocalTime(
            GraphQLDateTimeProperties configurationProperties) {
        final String name = configurationProperties.getLocalTime().getScalarName();
        if (name == null) {
            return new GraphQLLocalTime();
        } else {
            return new GraphQLLocalTime(name);
        }
    }

}
