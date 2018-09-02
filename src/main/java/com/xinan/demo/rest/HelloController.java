package com.xinan.demo.rest;

import com.xinan.demo.util.Try;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xinan
 * @date 2018/8/3
 */
@RestController
@RequestMapping("hello")
@Slf4j
public class HelloController {


    private AtomicInteger stock = new AtomicInteger(10);
    private AtomicInteger order = new AtomicInteger(0);

    @GetMapping
    public Mono<String> say(String name) {
        return Mono.just(name)
            .publishOn(Schedulers.elastic())
            .map(Try.of(this::hello));
    }

    private String hello(String name) throws InterruptedException {
        Thread.sleep(10000);
        String result = String.format("hello %s, current-thread is [%s]", name, Thread.currentThread().getName());
        System.out.println(result);
        return result;
    }

    @PostMapping("error")
    public Mono<String> testError() {
        throw new RuntimeException("test error");
    }

    @ExceptionHandler
    public void test() {

    }

    @PostMapping
    public Mono<String> testPost(String name) {
        return Mono.just("post from " + name);
    }

    @GetMapping("nob")
    public Mono<String> nob() {
        return Mono.just(Thread.currentThread().getName());
    }

    @GetMapping("order")
    public Mono<Integer> order() {
        return Mono.fromSupplier(Try.of(this::decrementAndGetStock))
            .map(Try.of(this::incrAndGetOrder))
            .subscribeOn(Schedulers.elastic())
            .doOnError(t -> log.error("order error: ", t))
            .onErrorReturn(-1);
    }

    private Integer decrementAndGetStock() throws InterruptedException {
        Thread.sleep(1000);
        System.out.printf("[stock], current-thread is [%s]\n", Thread.currentThread().getName());
        return stock.decrementAndGet();
    }

    private Integer incrAndGetOrder(Integer stockAfterDecr) throws InterruptedException {
        if (stockAfterDecr < 0) {
            stock.incrementAndGet();
            throw new RuntimeException("stock over limit");
        }
        Thread.sleep(1000);
        System.out.printf("[order], current-thread is [%s]\n", Thread.currentThread().getName());
        return order.incrementAndGet();
    }

    public static void main(String[] args) {
        String a = "http:2";
        System.out.println(handleUrl(a));
        System.out.println(a);
    }

    private static String handleUrl(String url) {
        url = StringUtils.removeStartIgnoreCase(url, "http:");
        url = StringUtils.removeStartIgnoreCase(url, "https:");
        return url;
    }


}


