package com.nobody.utils.config;


import com.nobody.utils.JWTUtils;
import com.nobody.utils.snowflake.SnowflakeProperties;
import com.nobody.utils.snowflake.SnowflakeUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SnowflakeProperties.class)
public class GeneralConfig {

    @Bean
    public JWTUtils jwtUtils(){
        return new JWTUtils();
    }

    @Bean
    public SnowflakeUtils snowflakeUtils(SnowflakeProperties snowflakeProperties){
        return new SnowflakeUtils(snowflakeProperties);
    }

}
