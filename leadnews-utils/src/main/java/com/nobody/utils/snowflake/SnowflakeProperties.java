package com.nobody.utils.snowflake;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "snowflake")
@Data
public class SnowflakeProperties {

    private long dataCenterId;

    private long machineId;
}
