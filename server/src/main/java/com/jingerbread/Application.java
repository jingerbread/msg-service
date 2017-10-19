package com.jingerbread;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan( basePackages = { "com.jingerbread.controllers" } )
@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        System.out.println("Application started");
        SpringApplication.run(Application.class, args);
    }
}
