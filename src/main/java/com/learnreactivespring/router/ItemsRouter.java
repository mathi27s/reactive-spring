package com.learnreactivespring.router;


import com.learnreactivespring.constants.ItemConstants;
import com.learnreactivespring.handler.ItemHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static com.learnreactivespring.constants.ItemConstants.END_POINT_func;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class ItemsRouter {

    @Bean
    public RouterFunction<ServerResponse> itemsRouter(ItemHandler itemHandler) {
        return RouterFunctions.
                route(GET(END_POINT_func).and(accept(MediaType.APPLICATION_JSON))
                        , itemHandler::getAll);
    }
}
