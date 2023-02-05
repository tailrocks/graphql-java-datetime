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
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Adam Setch
 * @author Alexey Zhokhov
 */
@AutoConfiguration
public class GraphQlDateTimeAutoConfiguration {

    @Bean
    public DateTimeScalarRegistrar dateTimeScalarsRegistrar(
            GraphQLScalarType graphQlDateScalar,
            GraphQLScalarType graphQlLocalDateScalar,
            GraphQLScalarType graphQlLocalDateTimeScalar,
            GraphQLScalarType graphQlLocalTimeScalar,
            GraphQLScalarType graphQlOffsetDateTimeScalar,
            GraphQLScalarType graphQlYearMonthScalar,
            GraphQLScalarType graphQlDurationScalar
    ) {
        return new AbstractDateTimeScalarRegistrar() {
            @Override
            public List<GraphQLScalarType> getScalars() {
                List<GraphQLScalarType> scalars = new ArrayList<>();
                scalars.add(graphQlDateScalar);
                scalars.add(graphQlLocalDateScalar);
                scalars.add(graphQlLocalDateTimeScalar);
                scalars.add(graphQlLocalTimeScalar);
                scalars.add(graphQlOffsetDateTimeScalar);
                scalars.add(graphQlYearMonthScalar);
                scalars.add(graphQlDurationScalar);
                return scalars;
            }
        };
    }

    @DgsComponent
    @FunctionalInterface
    public interface DateTimeScalarRegistrar {
        List<GraphQLScalarType> getScalars();
    }

    public abstract class AbstractDateTimeScalarRegistrar implements DateTimeScalarRegistrar {

        @DgsRuntimeWiring
        public RuntimeWiring.Builder addScalar(RuntimeWiring.Builder builder) {
            for (GraphQLScalarType scalarType : getScalars()) {
                builder.scalar(scalarType);
            }
            return builder;
        }

    }

}
