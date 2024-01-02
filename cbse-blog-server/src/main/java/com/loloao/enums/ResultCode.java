package com.loloao.enums;

public enum ResultCode {
    /* 成功状态码 */
    SUCCESS(0, "success"),
    ERROR(1, "fail"),

    /* 参数错误：10001-19999 */
    PARAM_IS_INVALID(10001, "param is invalid"),
    PARAM_IS_BLANK(10002, "param is blank"),
    PARAM_TYPE_BIND_ERROR(10003, "param type bind error"),
    PARAM_NOT_COMPLETE(10004, "param not complete"),


    /* 用户错误：20001-29999*/
    USER_NOT_LOGGED_IN(20001, "user not login"),
    USER_LOGIN_ERROR(20002, "username or password error"),
    USER_ACCOUNT_FORBIDDEN(20003, "account been blocked"),
    USER_NOT_EXIST(20004, "user not exists"),
    USER_HAS_EXISTED(20005, "user already existed"),
    USER_Register_ERROR(20006, "user registration error"),

    /* 业务错误：30001-39999 */
    SPECIFIED_QUESTIONED_USER_NOT_EXIST(30001, "specified questioned user not exist"),

    /* 系统错误：40001-49999 */
    SYSTEM_INNER_ERROR(40001, "system inner error, please try again later"),

    /* 数据错误：50001-599999 */
    RESULE_DATA_NONE(50001, "data not found"),
    DATA_IS_WRONG(50002, "data is not correct"),
    DATA_ALREADY_EXISTED(50003, "data already existed"),

    /* 接口错误：60001-69999 */
    INTERFACE_INNER_INVOKE_ERROR(60001, "inner interface invoke error"),
    INTERFACE_OUTTER_INVOKE_ERROR(60002, "outer interface invoke error"),
    INTERFACE_FORBID_VISIT(60003, "interface forbid visit"),
    INTERFACE_ADDRESS_INVALID(60004, "interface address invalid"),
    INTERFACE_REQUEST_TIMEOUT(60005, "interface request timeout"),
    INTERFACE_EXCEED_LOAD(60006, "interface overload"),

    /* 权限错误：70001-79999 */
    PERMISSION_NO_ACCESS(70001, "no permission"),

    /* 文件上传 */
    UPLOAD_ERROR(80001, "upload failed"),

    SESSION_TIME_OUT(90001, "Session timeout");

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

    public static String getMessage(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.message;
            }
        }
        return name;
    }

    public static Integer getCode(String name) {
        for (ResultCode item : ResultCode.values()) {
            if (item.name().equals(name)) {
                return item.code;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
