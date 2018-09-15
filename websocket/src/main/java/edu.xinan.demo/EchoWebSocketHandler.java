/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.xinan.demo;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

/**
 * @author xinan
 * @date 2018/9/3
 */
public class EchoWebSocketHandler implements WebSocketHandler {

    public EchoWebSocketHandler() {
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        // Use retain() for Reactor Netty
        return session.send(session.receive().doOnNext(WebSocketMessage::retain));
    }
}
