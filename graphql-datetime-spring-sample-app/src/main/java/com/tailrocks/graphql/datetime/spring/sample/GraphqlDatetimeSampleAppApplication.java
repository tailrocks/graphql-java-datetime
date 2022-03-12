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
package com.tailrocks.graphql.datetime.spring.sample;

import com.tailrocks.graphql.datetime.DateScalar;
import com.tailrocks.graphql.datetime.DurationScalar;
import com.tailrocks.graphql.datetime.LocalDateScalar;
import com.tailrocks.graphql.datetime.LocalDateTimeScalar;
import com.tailrocks.graphql.datetime.LocalTimeScalar;
import com.tailrocks.graphql.datetime.OffsetDateTimeScalar;
import com.tailrocks.graphql.datetime.YearMonthScalar;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

/**
 * @author Alexey Zhokhov
 */
@SpringBootApplication
public class GraphqlDatetimeSampleAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlDatetimeSampleAppApplication.class, args);
    }

    @Bean
    public RuntimeWiringConfigurer graphqlDateTimeConfigurer() {
        return new RuntimeWiringConfigurer() {
            @Override
            public void configure(RuntimeWiring.Builder builder) {
                // TODO
                builder.scalar(DateScalar.create("MyDate"));
                builder.scalar(DurationScalar.create(null));
                // TODO
                builder.scalar(LocalDateScalar.create("MyLocalDate", false, null));
                builder.scalar(LocalDateTimeScalar.create(null, false, null));
                builder.scalar(LocalTimeScalar.create(null));
                builder.scalar(OffsetDateTimeScalar.create(null));
                builder.scalar(YearMonthScalar.create(null));
            }
        };
    }

}
