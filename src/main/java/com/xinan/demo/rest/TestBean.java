package com.xinan.demo.rest;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class TestBean {

    String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime lage;
}
