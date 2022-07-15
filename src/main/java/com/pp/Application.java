package com.pp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}

//TODO 前后端传输文件报错  the request was rejected because no multipart boundary was found
//TODO 自动多行注释
//TODO 跨域重定向的问题