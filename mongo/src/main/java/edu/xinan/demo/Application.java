package edu.xinan.demo;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableMongoAuditing
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}


@Component
@Slf4j
class DataInitializer implements CommandLineRunner {

    private final PostRepository posts;

    public DataInitializer(PostRepository posts) {
        this.posts = posts;
    }

    @Override
    public void run(String[] args) {
        log.info("start data initialization  ...");
        this.posts
          .deleteAll()
          .thenMany(
            Flux
              .just("bianzhaoyu", "xinan")
              .flatMap(
                name -> this.posts.save(Post.builder().name(name).age(25).build())
              )
          )
          .log()
          .subscribe(
            null,
            null,
            () -> log.info("done initialization...")
          );

    }

}

@RestController()
@RequestMapping(value = "/posts")
class PostController {

    private final PostRepository posts;

    public PostController(PostRepository posts) {
        this.posts = posts;
    }

    @GetMapping("")
    public Flux<Post> all() {
        return this.posts.findAll();
    }

    @PostMapping("")
    public Mono<Post> create(@RequestBody Post post) {
        return this.posts.save(post);
    }

    @GetMapping("/{id}")
    public Mono<Post> get(@PathVariable("id") String id) {
        return this.posts.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<Post> update(@PathVariable("id") String id, @RequestBody Post post) {
        return this.posts.findById(id)
          .map(p -> {
              p.setName(post.getName());
              p.setAge(post.getAge());

              return p;
          })
          .flatMap(p -> this.posts.save(p));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return this.posts.deleteById(id);
    }

}

interface PostRepository extends ReactiveMongoRepository<Post, String> {
}

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
class Post {

    @Id
    private String id;
    private String name;
    private Integer age;

    @CreatedDate
    private LocalDateTime createdDate;
}

