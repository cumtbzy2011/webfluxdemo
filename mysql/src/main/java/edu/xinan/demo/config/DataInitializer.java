package edu.xinan.demo.config;

import edu.xinan.demo.rest.Post;
import edu.xinan.demo.rest.RxJava2PostRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

/**
 *
 * @author xinan
 */
@Component
@Slf4j
public class DataInitializer {

    private final RxJava2PostRepository posts;

    public DataInitializer(RxJava2PostRepository posts) {
        this.posts = posts;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void initPosts() {
        log.info("initializing posts data...");
        Stream.of("bianzhaoyu", "xinan").forEach(
            name -> this.posts.save(Post.builder().name(name).age(25).build())
                .subscribe()
        );
    }

}
