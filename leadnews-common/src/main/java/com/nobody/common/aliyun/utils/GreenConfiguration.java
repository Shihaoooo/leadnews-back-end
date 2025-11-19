package com.nobody.common.aliyun.utils;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GreenProperties.class)
public class GreenConfiguration {

    @Bean
    public GreenImageScan greenImageScan(GreenProperties greenProperties){
        return new GreenImageScan(greenProperties);
    }

    @Bean
    public GreenTextScan greenTextScan(GreenProperties greenProperties){
        return new GreenTextScan(greenProperties);
    }


}
