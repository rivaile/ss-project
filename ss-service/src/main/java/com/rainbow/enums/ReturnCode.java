package com.rainbow.enums;

/**
 * The enum Return code.
 */
public enum ReturnCode {

    // ~~~~~~~~~~~~~~~~~~~~~COMMON 错误，编号1~5开头，采用3位数字~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    SUCCESS("200", "Success"),
    BAD_REQUEST("400", "请求格式错误！"),
    UNAUTHORIZED("401", "鉴权信息错误！"),
    NOT_FOUND("404", "没有找到服务！"),
    METHOD_NOT_ALLOWED("405", "仅支持POST请求！"),
    UNSUPPORTED_MEDIA_TYPE("415", "仅支持application/json！"),
    TOO_MANY_REQUESTS("429", "当前请求过多！"),
    PAYLOAD_TOO_LARGE("413", "请求内容过大！"),
    TARGET_SERVER_UNAVAILABLE("494", "目标服务不可用！"),
    INTERNAL_SERVER_ERROR("500", "系统未知错误！"),
    INTERNAL_SERVER_PARAMS("430", "参数错误！"),
    PENETRATION_ERROR("99", "底层系统抛出可穿透到前端！"),
    SERVICE_LIMIT("101", "服务降级！"),

    /**
     * 子系统错误码定义 编码段区分
     * 七位，10开头基础的一些服务（认证、网关、产品配置），20、30...各个子系统定义
     * 权限认证服务：  1010001-1020000
     * 网关服务web:    1020001-1030000
     * 网关服务mobile: 1030001-1040000
     * 网关服务wechat：1040001-1050000
     * 基础服务：      1050001-1060000
     */
    // ~~~~~~~~~~~~~~~~~~~~~AUTH_SERVER 错误编码段，权限认证服务：  1010001-1020000~~~~~~~~~~~~~
    AUTH_SERVER_1010001("1010001", "用户名或密码错误！"),
    AUTH_SERVER_1010002("1010002", "登录异常！"),
    AUTH_SERVER_1010003("1010003", "Token已经过期！"),
    AUTH_SERVER_1010004("1010004", "FreshToken已经过期,需要重新登录！"),
    AUTH_SERVER_1010005("1010005", "无效的Token！"),
    AUTH_SERVER_1010006("1010006", "用户名或密码不能为空！"),
    AUTH_SERVER_1010007("1010007", "验证码不能为空！"),
    AUTH_SERVER_1010008("1010008", "uuId不能为空！"),
    AUTH_SERVER_1010009("1010009", "验证码不正确！"),

    // ~~~~~~~~~~~~~~~~~~~~~GATEWAY WEB错误编码段，网关服务web:  1020001-1030000~~~~~~~~~~~~~~~
    GATE_WEB_1020001("1020001", "WEB网关错误！"),
    GATE_WEB_1020002("1020002", "用户没有所属门店，请联系管理员！"),
    GATE_WEB_1020003("1020003", "未知访问权限[%s]，请联系管理员！"),


    // ~~~~~~~~~~~~~~~~~~~~~GATEWAY MOBILE错误编码段，网关服务mobile: 1030001-1040000~~~~~~~//
    GATE_MOBILE_1030001("1030001", "MOBILE网关错误！"),

    //~~~~~~~~~~~~~~~~~~~~~GATEWAY WECHAT错误编码段，网关服务wechat：1040001-1050000~~~~~~~//
    GATE_WECHAT_1040001("1040001", "WECHAT网关错误！"),

    // ~~~~~~~~~~~~~~~~~~~~~SUPPORT_SERVER 错误编码段，基础服务： 1050001-1060000~~~~~~~~~~~~
    USERNAME_EXSIT_ERROR("1050001", "该用户名已存在！"),
    TELEPHONE_EXSIT_ERROR("1050002", "该手机号已存在！"),
    MAIL_EXSIT_ERROR("1050003", "该邮箱已存在！"),
    VERIFY_EXSIT_ERROR("1050004", "校验异常错误！"),

    ROLE_NAME_EXSIT_ERROR("1050004", "该角色名称已存在！");



    private final String value;
    private final String message;

    ReturnCode(String value, String message) {
        this.value = value;
        this.message = message;
    }

    /**
     * Value string.
     *
     * @return the string
     * @author wuiang
     */
    public String value() {
        return this.value;
    }

    /**
     * To string string.
     *
     * @return the string
     * @author wuiang
     */
    @Override
    public String toString() {
        return value;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
