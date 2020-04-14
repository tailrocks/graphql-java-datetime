package com.zhokhov.graphql.datetime.sample.webflux.schema;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Component
public class Mutation implements GraphQLMutationResolver {

    public CompletableFuture<LocalDateTime> plusDay(LocalDateTime input) {
        return Mono.just(input.plusDays(1)).toFuture();
    }

}
