package com.nobody.utils.config;

import com.nobody.utils.JWTUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneralConfig {

    @Bean
    public JWTUtils jwtUtils(){
        return new JWTUtils();
    }
}
