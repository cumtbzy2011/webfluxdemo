package com.xinan.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

/**
 * @author xinan
 * @date 2018/8/3
 */
@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public TestHandler testHandler() {
        return new TestHandler();
    }

    @Bean
    public RouterFunction<ServerResponse> routes(TestHandler testHandler) {
        return RouterFunctions.route(RequestPredicates.POST("/route"),
            testHandler::echoName);
    }

    @GetMapping("anno")
    public String sayHello() {
        return "hello world";
    }


    class TestHandler {

        public Mono<ServerResponse> echoName(ServerRequest request) {
            return ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("hello world"));
        }
    }

}


