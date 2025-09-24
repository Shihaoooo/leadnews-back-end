package com.nobody.common.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    /**
     * 配置 OpenAPI 文档信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // 文档基本信息
                .info(new Info()
                        .title("Leadnews-API管理平台") // 标题
                        .description("后台API接口文档") // 描述
                        .version("1.0.0") // 版本
                        // 联系人信息（可选）
                        .contact(new Contact()
                                .name("个人开发者:路小雨")
                                .email("") // 可补充邮箱
                                .url("") // 可补充网址
                        )
                );
    }
}