package com.tailrocks.graphql.datetime.spring.boot;

import graphql.schema.GraphQLScalarType;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Alexey Zhokhov
 */
@Configuration(proxyBeanMethods = false)
@AutoConfigureBefore({
        org.springframework.boot.autoconfigure.graphql.GraphQlAutoConfiguration.class,
        GraphQlDateTimeScalarsAutoConfiguration.class
})
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
