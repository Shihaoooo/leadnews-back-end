package com.nobody.model.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(name = "用户登陆实体类",description = "存储用户登陆信息")
public class Login {

    @Schema(description = "手机号")
    @NotBlank(message = "用户名不能为空")
    private String phone;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
