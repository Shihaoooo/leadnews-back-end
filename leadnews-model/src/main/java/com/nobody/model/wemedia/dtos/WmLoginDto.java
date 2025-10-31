package com.nobody.model.wemedia.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "自媒体微服务登陆Dto" )
public class WmLoginDto {

    @Schema(description = "用户名")
    private String name; // 用户名

    @Schema(description = "密码")
    private String password;
}
