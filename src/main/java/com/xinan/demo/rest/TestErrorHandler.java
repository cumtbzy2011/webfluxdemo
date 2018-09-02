package com.xinan.demo.rest;

import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author xinan
 * @date 2018/8/25
 */
@Component
//要比DefaultErrorWebExceptionHandler优先级-1高
    //比较底层，如果异常被@ExceptionHandler处理了，那么将不会由此处理
    //可以处理filter和webHandler中的异常
@Order(-2)
public class TestErrorHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        byte[] bytes = "error-test".getBytes(StandardCharsets.UTF_8);
        DataBuffer wrap = exchange.getResponse().bufferFactory().wrap(bytes);
        return exchange.getResponse().writeWith(Flux.just(wrap));
    }
}
