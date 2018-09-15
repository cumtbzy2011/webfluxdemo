/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.xinan.demo;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xinan
 */
@RestController
@RequestMapping("/uploads")
public class MultipartController {

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Mono<String> requestBodyFlux(@RequestBody Flux<Part> parts) {
        return parts.map(part -> part instanceof FilePart
              ? part.name() + ":" + ((FilePart) part).filename()
              : part.name())
          .collect(Collectors.joining(",", "[", "]"));
    }

    //如果不是application/stream json則呼叫端無法滾動得到結果，將一直阻塞等待資料流結束或超時。
    @GetMapping(value = "stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Post> getBeanStream() {
        return Flux.interval(Duration.ofMillis(500))
          .map(l -> new Post("bian", LocalDateTime.now()))
          .log();
    }
}

