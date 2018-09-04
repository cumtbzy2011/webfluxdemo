package com.xinan.demo.rest;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author xinan
 * @date 2018/8/25
 */
//@Component
public class TestFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        int i = 1 / 0;
        ServerWebExchange postExchange = exchange.mutate()
            .request(builder -> builder.method(HttpMethod.POST))
            .build();
        return chain.filter(postExchange);
    }
}
