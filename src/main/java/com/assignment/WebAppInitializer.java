package com.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@SpringBootApplication
public class WebAppInitializer {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(WebAppInitializer.class, args);
    }

}

