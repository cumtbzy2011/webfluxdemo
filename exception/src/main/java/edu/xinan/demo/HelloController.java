package edu.xinan.demo;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hello")
public class HelloController {

    @GetMapping("error")
    public String sayHello(String name) {
        int i = 1 / 0;
        return "controller: " + i;
    }

    @ExceptionHandler(Exception.class)
    public String test(Exception e) {
        return "@ExceptionHandler: " + e.getMessage();
    }
}
