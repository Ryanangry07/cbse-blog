package com.loloao.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"Success"),
    // 登录
    NEED_LOGIN(401,"Need login"),
    NO_OPERATOR_AUTH(403,"No permission"),
    SYSTEM_ERROR(500,"System error"),
    USERNAME_EXIST(501,"User already exists"),
    PHONE_NUMBER_EXIST(502,"Phone number already exists"), EMAIL_EXIST(503, "Email already exists"),
    REQUIRE_USERNAME(504, "Username is required"),
    LOGIN_ERROR(505,"Username or password error");
    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
