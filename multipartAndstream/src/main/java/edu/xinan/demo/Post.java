package edu.xinan.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;


@AllArgsConstructor
@Data
public class Post {
    String name;
    LocalDateTime date;
}

