package com.nobody.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LeadnewsUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeadnewsUserApplication.class, args);
    }

}
