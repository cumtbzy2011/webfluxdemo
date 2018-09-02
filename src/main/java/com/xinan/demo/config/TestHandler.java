package com.xinan.demo.config;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class TestHandler {

    public Mono<ServerResponse> echoName(ServerRequest request) {
        return ok()
          .contentType(MediaType.TEXT_PLAIN)
          .body(BodyInserters.fromObject("hello world"));
    }
}
