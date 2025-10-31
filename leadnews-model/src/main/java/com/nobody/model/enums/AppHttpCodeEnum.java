package com.nobody.model.enums;

import lombok.Getter;

public enum AppHttpCodeEnum {

    // ========== 登录相关（示例：密码错误） ==========
    SUCCESS(200, "操作成功"),
    NEED_LOGIN(2, "未登录，请先登录"),
    LOGIN_PASSWORD_ERROR(3, "密码错误"),
    DUPLICATE_ACCOUNT(4,"手机号已注册"),

    // ========== TOKEN 相关（50~100） ==========
    TOKEN_INVALID(50, "无效的TOKEN"),
    TOKEN_EXPIRE(51, "TOKEN已过期"),
    TOKEN_REQUIRE(52, "TOKEN是必须的"),

    // ========== SIGN 验签相关（100~120） ==========
    SIGN_INVALID(100, "无效的SIGN"),
    SIG_TIMEOUT(101, "SIGN已过期"),

    // ========== 参数错误相关（500~1000） ==========
    PARAM_REQUIRE(500, "缺少参数"),
    PARAM_INVALID(501, "无效参数"),
    PARAM_IMAGE_FORMAT_ERROR(502, "图片格式有误"),
    SERVER_ERROR(503, "服务器内部错误"),
    EMPTY_FILE(504, "上传的文件为空"),

    // ========== 数据错误相关（1000~2000） ==========
    DATA_EXIST(1000, "数据已经存在"),
    AP_USER_DATA_NOT_EXIST(1001, "ApUser数据不存在"),
    DATA_NOT_EXIST(1002, "数据不存在"),

    // ========== 权限错误相关（3000~3500） ==========
    NO_OPERATOR_AUTH(3000, "无权限操作"),
    NEED_ADMINID(3001, "需要管理员权限");

    // ---------- Getter 方法 ----------
    // ---------- 枚举属性 ----------
    @Getter
    private int code;           // 错误码
    private String errorMessage; // 错误描述

    // ---------- 构造方法 ----------
    // 枚举构造方法默认私有（也可省略 private，编译器会自动处理）
    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public String getMessage() {
        return errorMessage;
    }
}