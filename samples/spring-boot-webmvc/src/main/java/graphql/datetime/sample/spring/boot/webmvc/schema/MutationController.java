package graphql.datetime.sample.spring.boot.webmvc.schema;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class MutationController {

    @MutationMapping
    public LocalDateTime plusDay(@Argument LocalDateTime input) {
        return input.plusDays(1);
    }

}
