//package com.xinan.demo.rest;
//
//import com.xinan.demo.util.Try;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.format.datetime.DateFormatter;
//import org.springframework.http.CacheControl;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Flux;
//import reactor.core.publisher.Mono;
//import reactor.core.scheduler.Schedulers;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.Date;
//import java.util.Locale;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * @author xinan
// * @date 2018/8/3
// */
//@RestController
//@RequestMapping("hello")
//@Slf4j
//public class HelloController {
//
//
//    private AtomicInteger stock = new AtomicInteger(10);
//    private AtomicInteger order = new AtomicInteger(0);
//
//    @GetMapping
//    public Mono<String> say(String name) {
//        return Mono.just(name)
//          .publishOn(Schedulers.elastic())
//          .map(Try.of(this::hello));
//    }
//
//    private String hello(String name) throws InterruptedException {
//        Thread.sleep(10000);
//        String result = String.format("hello %s, current-thread is [%s]", name, Thread.currentThread().getName());
//        System.out.println(result);
//        return result;
//    }
//
//    @PostMapping("error")
//    public Mono<String> testError() {
//        //两种异常方式都会被exceptionhandler处理
////        throw new RuntimeException("test error-1");
//        return Mono.error(new RuntimeException("test error-2"));
//    }
//
//    @ExceptionHandler(Exception.class)
//    public String test(Exception e) {
//        return "result: " + e.getMessage();
//    }
//
//    @PostMapping
//    public Mono<BigDecimal> testPost(String name, Date time, BigDecimal bd) {
//        System.out.println("bd:" + bd);
//        System.out.println("time: " + time);
//        return Mono.just(bd);
//    }
//
//
//
//    @PostMapping("bean")
//    public Mono<LocalDateTime> testPostBean(@RequestBody Mono<TestBean> testBean) {
//        return testBean.map(TestBean::getLage);
//    }
//
//    @PostMapping("bean2/{name}")
//    public ResponseEntity<TestBean> testCache(@PathVariable String name) {
//        TestBean result = new TestBean();
//        result.setName(name);
//        result.setAge(new Date());
//        result.setLage(LocalDateTime.now());
//        return ResponseEntity
//          .ok()
//          .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
//          .eTag(name)
//          .body(result);
//    }
//
//    @InitBinder
//    public void initBinder(WebDataBinder binder){
//        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
//    }
//
//
//    @GetMapping("nob")
//    public Mono<String> nob() {
//        return Mono.just(Thread.currentThread().getName());
//    }
//
//    @GetMapping("order")
//    public Mono<Integer> order() {
//        return Mono.fromSupplier(Try.of(this::decrementAndGetStock))
//          .map(Try.of(this::incrAndGetOrder))
//          .subscribeOn(Schedulers.elastic())
//          .doOnError(t -> log.error("order error: ", t))
//          .onErrorReturn(-1);
//    }
//
//    private Integer decrementAndGetStock() throws InterruptedException {
//        Thread.sleep(1000);
//        System.out.printf("[stock], current-thread is [%s]\n", Thread.currentThread().getName());
//        return stock.decrementAndGet();
//    }
//
//    private Integer incrAndGetOrder(Integer stockAfterDecr) throws InterruptedException {
//        if (stockAfterDecr < 0) {
//            stock.incrementAndGet();
//            throw new RuntimeException("stock over limit");
//        }
//        Thread.sleep(1000);
//        System.out.printf("[order], current-thread is [%s]\n", Thread.currentThread().getName());
//        return order.incrementAndGet();
//    }
//
//
//    //如果不是application/stream json則呼叫端無法滾動得到結果，將一直阻塞等待資料流結束或超時。
//    @GetMapping(value = "stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
//    public Flux<TestBean> getBeanStream() {
//        return Flux.interval(Duration.ofMillis(500))
//            .map(l -> new TestBean("bian", new Date(), LocalDateTime.now()))
//            .log();
//    }
//}
//
//
