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
package graphql.datetime.boot.test;

import com.oembedler.moon.graphql.boot.GraphQLJavaToolsAutoConfiguration;
import graphql.datetime.GraphQLDate;
import graphql.datetime.GraphQLLocalDate;
import graphql.datetime.GraphQLLocalDateTime;
import graphql.datetime.GraphQLLocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author <a href='mailto:alexey@zhokhov.com'>Alexey Zhokhov</a>
 */
public class ContextHelper {

    @Configuration
    @ComponentScan("graphql.datetime.boot")
    static class BaseConfiguration {

        // initialize date time types here
        @Autowired(required = false) GraphQLDate graphQLDate;
        @Autowired(required = false) GraphQLLocalDate graphQLLocalDate;
        @Autowired(required = false) GraphQLLocalDateTime graphQLLocalDateTime;
        @Autowired(required = false) GraphQLLocalTime graphQLLocalTime;

    }

    static public AbstractApplicationContext load() {
        AbstractApplicationContext context;

        try {
            context = AnnotationConfigApplicationContext.class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        AnnotationConfigRegistry registry = (AnnotationConfigRegistry) context;

        registry.register(BaseConfiguration.class);
        registry.register(GraphQLJavaToolsAutoConfiguration.class);

        context.refresh();

        return context;
    }

}
