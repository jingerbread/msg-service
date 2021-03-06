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
        System.out.println("******* Server application started, listening on kafka topic 'msg_output' *******");
        SpringApplication.run(Application.class, args);
    }
}
