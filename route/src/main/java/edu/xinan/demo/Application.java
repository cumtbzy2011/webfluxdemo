package edu.xinan.demo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

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
    public String sayHello(String name) {
        return "hello world! " + name;
    }


    class TestHandler {

        public Mono<ServerResponse> echoName(ServerRequest request) {
            return request.bodyToMono(Post.class)
              .map(Post::getName)
              .flatMap(name -> ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("hello world!" + name)));
        }
    }

}

@Data
@NoArgsConstructor
class Post {
    private String id;
    private String name;
}
