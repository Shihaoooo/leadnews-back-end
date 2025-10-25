package com.nobody.file.config;

import com.nobody.file.service.impl.MinIOStorageServer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinIOAutoConfiguration {

    @Bean
    public MinIOStorageServer MinIOStorageServiceImpl(MinioProperties minioProperties){
        return new MinIOStorageServer(minioProperties);

    }


}
