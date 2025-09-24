package com.nobody.model.pojos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(name = "用户实体类")
public class User implements Serializable {

    @Serial //该注解仅做标识
    @Schema(hidden = true)
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Integer id;

    @Schema(hidden = true)
    private String salt; // 密码，通信盐

    @Schema(description = "用户名")
    private String name;

    @Schema(description = "用户密码")
    private String password;

    @Schema(description = "用户手机号")
    private String phone;

    @Schema(description = "头像")
    private String image;

    @Schema(description = "性别")
    private Integer sex;
    private Integer isCertification;
    private Integer isIdentityAuthentication;
    private Integer status;
    private Integer flag;
    private LocalDateTime dateTime;
}