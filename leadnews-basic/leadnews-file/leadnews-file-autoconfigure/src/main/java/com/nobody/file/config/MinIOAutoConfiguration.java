package com.nobody.file.config;

import com.nobody.file.service.impl.MinIOStorageServer;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinioProperties.class)
public class MinIOAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public MinIOStorageServer MinIOStorageServiceImpl(MinioProperties minioProperties){
        return new MinIOStorageServer(minioProperties);

    }

    /*
    * 如果没有EnableConfigurationProperties注解，那么就要手动注入这个依赖类为Bean，那个注解可以自动注册Bean */


}
