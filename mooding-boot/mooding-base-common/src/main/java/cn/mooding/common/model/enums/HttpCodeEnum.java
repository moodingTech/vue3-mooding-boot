package cn.mooding.common.model.enums;

/**
 * 请求返回结果枚举
 *
 * @Author BlueFire
 * @Date 21/3/2021 -上午9:22
 */
public enum HttpCodeEnum {
    // 成功段200
    SUCCESS(200, "操作成功"),

    // 登录段1~50
    LOGIN_USER_NOT_EXIST(12, "该用户不存在"),
    LOGIN_USER_DEL(14, "该用户已经注销"),
    LOGIN_USER_FREEZE(16, "该用户已冻结"),
    UNAUTHORIZED(401,"未授权"),

    // 用户相关段 51~100
    USER_NAME_EXIST(51,"登录账号已存在"),
    EMAIL_EXIST(51,"邮箱已经存在"),
    PHONE_EXIST(51,"电话号码已经存在"),
    // 参数错误 500~1000
    PARAM_REQUIRE(500, "缺少参数"),
    PARAM_INVALID(501, "无效参数"),
    PARAM_ERR(502, "参数错误"),
    SERVER_ERROR(503, "服务器内部错误"),

    // 数据错误 1000~2000
    DATA_EXIST(1000, "数据已经存在"),
    AP_USER_DATA_NOT_EXIST(1001, "ApUser数据不存在"),
    DATA_NOT_EXIST(1002, "数据不存在"),
    DATA_TOO_LENGTH(1003, "字段太长,超出数据库字段的长度");

    int code;
    String message;

    HttpCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
