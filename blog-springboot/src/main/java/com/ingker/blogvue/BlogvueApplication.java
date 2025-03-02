package com.ingker.blogvue;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan
public class BlogvueApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogvueApplication.class, args);
    }
}
