package com.rainbow.enums;

/**
 * The enum Return code.
 *
 * @author wujiang
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
     * 产品配置服务：  1060001-1070000
     * 附件服务：      1070001-1080000
     * 订单服务 ：     1080001-1090000
     * 第三方服务 ：   1090001-1100000
     * 风控服务 ：     1100001-1200000
     */
    // ~~~~~~~~~~~~~~~~~~~~~AUTH_SERVER 错误编码段，权限认证服务：  1010001-1020000~~~~~~~~~~~~~//
    AUTH_SERVER_1010001("1010001", "用户名或密码错误！"),
    AUTH_SERVER_1010002("1010002", "登录异常！"),
    AUTH_SERVER_1010003("1010003", "Token已经过期！"),
    AUTH_SERVER_1010004("1010004", "FreshToken已经过期,需要重新登录！"),
    AUTH_SERVER_1010005("1010005", "无效的Token！"),
    AUTH_SERVER_1010006("1010006", "用户名或密码不能为空！"),
    AUTH_SERVER_1010007("1010007", "验证码不能为空！"),
    AUTH_SERVER_1010008("1010008", "uuId不能为空！"),
    AUTH_SERVER_1010009("1010009", "验证码不正确！"),


    // ~~~~~~~~~~~~~~~~~~~~~AUTH_SERVER 错误编码段，权限认证服务：  1010001-1020000~~~~~~~~~~~~~//


    // ~~~~~~~~~~~~~~~~~~~~~GATEWAY WEB错误编码段，网关服务web:    1020001-1030000~~~~~~~~~~~~~~~//
    GATE_WEB_1020001("1020001", "WEB网关错误！"),
    GATE_WEB_1020002("1020002", "用户没有所属门店，请联系管理员！"),
    GATE_WEB_1020003("1020003", "未知访问权限[%s]，请联系管理员！"),


    // ~~~~~~~~~~~~~~~~~~~~~GATEWAY WEB错误编码段，web: 网关服务web:    1020001-1030000~~~~~~~~~~~~~~~//


    // ~~~~~~~~~~~~~~~~~~~~~GATEWAY MOBILE错误编码段，网关服务mobile: 1030001-1040000~~~~~~~//
    GATE_MOBILE_1030001("1030001", "MOBILE网关错误！"),


    // ~~~~~~~~~~~~~~~~~~~~~GATEWAY MOBILE错误编码段，网关服务mobile: 1030001-1035000~~~~~~~//

    // ~~~~~~~~~~~~~~~~~~~~~GATEWAY ATTACHMENT错误编码段，网关服务ATTACHMENT: 1035001-1040000~~~~~~~//
    GATE_ATTACH_1035001("1035001", "ATTACHMENT网关错误！"),
    GATE_ATTACH_1035002("1035002", "请选择文件！"),
    GATE_ATTACH_1035003("1035003", "最大支持20个附件同时上传！"),
    GATE_ATTACH_1035004("1035004", "%s！"),

    // ~~~~~~~~~~~~~~~~~~~~~GATEWAY ATTACHMENT错误编码段，网关服务ATTACHMENT: 1035001-1040000~~~~~~~//


    //~~~~~~~~~~~~~~~~~~~~~GATEWAY WECHAT错误编码段，网关服务wechat：1040001-1050000~~~~~~~//
    GATE_WECHAT_1040001("1040001", "WECHAT网关错误！"),


    //~~~~~~~~~~~~~~~~~~~~~GATEWAY WECHATE错误编码段，网关服务wechat：1040001-1050000 ~~~~~~~//


    // ~~~~~~~~~~~~~~~~~~~~~SUPPORT_SERVER 错误编码段，基础服务：  1050001-1060000~~~~~~//
    SUPPORT_SERVER_1050001("1050001", "%s参数不能为空！"),
    SUPPORT_SERVER_1050002("1050002", "%s记录已经存在！"),
    SUPPORT_SERVER_1050003("1050003", "%s记录不存在！"),
    SUPPORT_SERVER_1050004("1050004", "%s参数不合法！"),
    SUPPORT_SERVER_1050005("1050005", "%s加解密异常！"),

    // ~~~~~~~~~~~~~~~~~~~~~SUPPORT_SERVER 错误编码段，基础服务： 1050001-1060000~~~~~~~~~~~~//


    // ~~~~~~~~~~~~~~~~PRODUCT_CONFIG_SERVER 错误编码段，产品配置服务：  1060001-1070000~~~~~~//
    PRODUCT_CONFIG_SERVER_1060001("1060001", "具体的错误描述"),


    // ~~~~~~~~~~~~~~~~PRODUCT_CONFIG_SERVER 错误编码段，产品配置服务：  1060001-1070000~~~~~//

    // ~~~~~~~~~~~~~~~~ATTACHMENT_SERVER 错误编码段，附件服务：  1070001-1080000~~~~~//
    ATTACHMENT_SERVER_1070001("1070001", "%s记录已存在"),
    ATTACHMENT_SERVER_1070002("1070002", "%s记录不存在"),
    ATTACHMENT_SERVER_1070003("1070003", "必传附件%s为空"),
    ATTACHMENT_SERVER_1070004("1070004", "暂无附件信息"),
    ATTACHMENT_SERVER_1070005("1070005", "无需上传附件"),


    // ~~~~~~~~~~~~~~~~PRODUCT_ORDER_SERVER 错误编码段，订单服务：  1080001-1090000~~~~~//
    ORDER_SERVER_1080001("1080001", "追加贷校验条件--%s"),
    ORDER_SERVER_1080002("1080002", "高危客户验证--%s"),
    ORDER_SERVER_1080003("1080003", "订单不存在"),
    ORDER_SERVER_1080004("1080004", "%s请求参数不能为空"),
    ORDER_SERVER_1080005("1080005", "芝麻信用分大于0时，芝麻信用分附件必填上传！"),
    ORDER_SERVER_1080006("1080006", "正在提交中，请耐心等待！"),
    ORDER_SERVER_1080007("1080007", "创建风控订单异常，请稍后再试！"),
    ORDER_SERVER_1080008("1080008", "没有权限或订单不在此节点！"),
    ORDER_SERVER_1080009("1080009", "必填信息不能为空！"),
    ORDER_SERVER_1080010("1080010", "车辆未评估，请先进行车评！"),
    ORDER_SERVER_1080011("1080011", "%s"),
    ORDER_SERVER_1080012("1080012", "存在未电核的联系人！"),


    // ~~~~~~~~~~~~~~~~第三方接口服务错误码 错误编码段：  1090000-1100000~~~~~//
    THIRD_PARTY_SERVER_1090001("1090001", "%s参数不能为空！"),
    THIRD_PARTY_SERVER_1090002("1090002", "%s参数格式不正确！"),
    THIRD_PARTY_SERVER_1090003("1090003", "请求出现异常！"),
    THIRD_PARTY_API_ERROR("1090000", "第三方服务接口异常"),
    THIRD_PARTY_DCM_ACCESS_001("1090101", "客户不符合准入条件,%s"),
    THIRD_PARTY_DCM_ACCESS_002("1090102", "%s,不能申请36期产品"),
    THIRD_PARTY_DCM_ACCESS_003("1090103", "%s，请选择短期限质押产品"),
    THIRD_PARTY_DCM_ACCESS_004("1090104", "参数异常,%s"),

    // ------------ 风控服务错误码--------


    // ------------ 风控服务错误码段：   1100001-1200000--------//
    RISK_SERVER_1100001("1100001", "%s请求参数不能为空！"),
    RISK_SERVER_1100002("1100002", "无效%s"),
    RISK_SERVER_1100003("1100003", "%s不存在！"),
    RISK_SERVER_1100004("1100004", "订单不在当前节点！"),
    RISK_SERVER_1100005("1100005", "审批金额不能大于最大审批金额!"),
    RISK_SERVER_1100006("1100006", "该订单受理人不为当前用户!"),
    RISK_SERVER_1100007("1100007", "该订单已被处理!"),
    RISK_SERVER_1100008("1100008", "该订单不存在分单信息!"),
    RISK_SERVER_1100009("1100009", "文件不存在!");

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
