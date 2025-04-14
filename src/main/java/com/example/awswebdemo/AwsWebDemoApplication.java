package com.example.awswebdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.mybatis.spring.annotation.MapperScan;


@MapperScan("com.example.awswebdemo.mapper")
@SpringBootApplication
@RestController
public class AwsWebDemoApplication {



    public static void main(String[] args) {
        SpringApplication.run(AwsWebDemoApplication.class, args);
    }
}