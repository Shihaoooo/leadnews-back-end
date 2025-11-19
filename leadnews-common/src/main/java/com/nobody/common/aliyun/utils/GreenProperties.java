package com.nobody.common.aliyun.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "aliyun.green")
@Component
@Data
public class GreenProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;

}
