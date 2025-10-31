package com.nobody.wemedia.config;

import com.nobody.utils.ThreadLocalUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfig {
    @Bean
    public ThreadLocalUtils<?> threadLocalUtils(){
        return new ThreadLocalUtils<>();
    }
}
