package com.nobody.leadnewsfeignapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LeadnewsFeignApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LeadnewsFeignApiApplication.class, args);
    }

}
