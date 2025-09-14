package com.nobody.leadnewsmodel.pojo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.net.Inet4Address;
import java.time.LocalDateTime;

@Data
public class User implements Serializable {

    @Serial //该注解仅做标识
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String salt; // 密码，通信盐
    private String name;
    private String password;
    private String phone;
    private String image;
    private Integer sex;
    private Integer isCertification;
    private Integer isIdentityAuthentication;
    private Integer status;
    private Integer flag;
    private LocalDateTime dateTime;
}