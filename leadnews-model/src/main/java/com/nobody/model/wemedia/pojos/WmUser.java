package com.nobody.model.wemedia.pojos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

public class WmUser {

    @Schema(description = "主键ID")
    private Integer id;

    @Schema(description = "用户ID")
    private Integer apUserId;

    @Schema(description = "作者ID")
    private Integer apAuthorId;

    @Schema(description = "登陆用户名")
    private String name;

    @Schema(description = "登陆密码")
    private String password;

    @Schema(description = "加密盐")
    private String salt;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String image;

    @Schema(description = "归属地")
    private String location;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "状态" ,example = "0:暂时不可用;1:永久不可用;2:正常可用")
    private Integer status;

    @Schema(description = "账号类型" ,example = "0:个人;1:公司;2:子账号")
    private String type;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "运营评分")
    private Integer score;

    @Schema(description = "最后一次登陆时间")
    private Date loginTime;

    @Schema(description = "创建时间")
    private Date createdTime;

}
