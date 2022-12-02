package com.tailrocks.graphql.datetime;

import graphql.Internal;

@Internal
final class CoercingUtil {

    private CoercingUtil() {
    }

    static String typeName(Object input) {
        return input == null ? "null" : input.getClass().getSimpleName();
    }

}
