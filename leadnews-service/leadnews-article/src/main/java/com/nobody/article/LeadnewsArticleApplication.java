package com.nobody.article;

import com.nobody.article.sevice.impl.ApArticleServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableFeignClients
@EnableAsync
public class LeadnewsArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeadnewsArticleApplication.class);
    }
}
