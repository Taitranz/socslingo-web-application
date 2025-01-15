package com.socslingo.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.socslingo.website")
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);

        
    }

}
