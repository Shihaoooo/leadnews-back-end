//package com.nobody.common.swagger;
//
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import org.springdoc.core.models.GroupedOpenApi;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class KnifeConfiguration {
//    @Bean
//    public OpenAPI customOpenAPI() {
//        return new OpenAPI()
//                .info(new Info()
//                        .title("leadnewsAPI文档")
//                        .version("1.0")
//                );
//    }
//
//    // 无需显式指定 packagesToScan，默认扫描主包下所有 @RestController
//    @Bean
//    public GroupedOpenApi defaultApi() {
//        return GroupedOpenApi.builder()
//                .group("leadnews接口")
//                .build();
//    }
//}
