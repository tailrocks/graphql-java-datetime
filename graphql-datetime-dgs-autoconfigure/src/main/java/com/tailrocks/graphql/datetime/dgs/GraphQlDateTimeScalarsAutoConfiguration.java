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
package com.tailrocks.graphql.datetime.dgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;
import com.tailrocks.graphql.datetime.DateScalar;
import com.tailrocks.graphql.datetime.DurationScalar;
import com.tailrocks.graphql.datetime.LocalDateScalar;
import com.tailrocks.graphql.datetime.LocalDateTimeScalar;
import com.tailrocks.graphql.datetime.LocalTimeScalar;
import com.tailrocks.graphql.datetime.OffsetDateTimeScalar;
import com.tailrocks.graphql.datetime.YearMonthScalar;
import graphql.schema.idl.RuntimeWiring;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Adam Setch
 */
@Configuration(proxyBeanMethods = false)
@DgsComponent
@EnableConfigurationProperties(GraphQlDateTimeProperties.class)
public class GraphQlDateTimeScalarsAutoConfiguration {

    @Autowired
    GraphQlDateTimeProperties configProps;

    @DgsRuntimeWiring
    public RuntimeWiring.Builder addScalars(RuntimeWiring.Builder builder) {

        DateTimeFormatter localDateFormatter = null;
        DateTimeFormatter localDateTimeFormatter = null;

        if (configProps.getLocalDate()
            .getFormat() != null) {
            localDateFormatter = DateTimeFormatter.ofPattern(configProps.getLocalDate()
                .getFormat());
        }

        if (configProps.getLocalDateTime()
            .getFormat() != null) {
            localDateTimeFormatter = DateTimeFormatter.ofPattern(configProps.getLocalDateTime()
                .getFormat());
        }

        return builder.scalar(DateScalar.create(configProps.getDate()
                .getScalarName()))
            .scalar(LocalDateScalar.create(configProps.getLocalDate()
                .getScalarName(), configProps.isZoneConversionEnabled(), localDateFormatter))
            .scalar(LocalDateTimeScalar.create(configProps.getLocalDateTime()
                .getScalarName(), configProps.isZoneConversionEnabled(), localDateTimeFormatter))
            .scalar(LocalTimeScalar.create(configProps.getLocalTime()
                .getScalarName()))
            .scalar(OffsetDateTimeScalar.create(configProps.getOffsetDateTime()
                .getScalarName()))
            .scalar(YearMonthScalar.create(configProps.getYearMonth()
                .getScalarName()))
            .scalar(DurationScalar.create(configProps.getDuration()
                .getScalarName()));
    }
}
