package com.nobody.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LeadnewsArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeadnewsArticleApplication.class);
    }
}
