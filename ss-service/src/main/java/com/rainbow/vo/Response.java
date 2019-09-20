package com.rainbow.vo;

import com.rainbow.enums.ReturnCode;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;


/**
 * 通用响应（同步）报文结构
 *
 * @param <T> the type parameter
 * @author wujiang
 * @version 1.0
 * @created 30-七月-2015 16:15:24
 */
public class Response<T> implements Serializable {
    /**
     * 返回错误码，具体的编码规范参见"系统设计"
     */
    @ApiModelProperty(value = "返回编码", required = true)
    private String status;
    @ApiModelProperty(value = "返回消息", required = true)
    private String msg;
    @ApiModelProperty(value = "错误详情", required = true)
    private String error;
    @ApiModelProperty(value = "返回内容", required = true)
    private T result;
    @ApiModelProperty(value = "返回时间", required = true)
    private final Date timestamp = new Date();
    @ApiModelProperty(value = "返回请求状态", required = true)
    private boolean isSuccess;

    /**
     * 创建一个状态为成功的响应
     *
     * @param message 响应消息
     * @return 响应对象
     */
    public static <T> Response<T> success(String message) {
        Response<T> rsp = new Response<>();
        rsp.setCode(ReturnCode.SUCCESS);
        rsp.setMsg(message);
        return rsp;
    }

    /**
     * 创建一个状态为成功的响应
     *
     * @param result 返回内容
     */
    public static <T> Response<T> success(T result) {
        Response<T> rsp = new Response<>();
        rsp.setCode(ReturnCode.SUCCESS);
        rsp.setResult(result);
        return rsp;
    }

    /**
     * 创建一个状态为成功的响应
     * isSuccess 字段为true
     * status 为不同的业务编码
     *
     * @param result 返回内容
     */
    public static <T> Response<T> success(ReturnCode code, T result) {
        Response<T> rsp = new Response<>();
        rsp.setSuccess(true);
        rsp.setCode(code);
        rsp.setResult(result);
        return rsp;
    }

    /**
     * 创建一个状态为成功的响应
     *
     * @param result  返回内容
     * @param message 响应消息
     */
    public static <T> Response<T> success(T result, String message) {
        Response<T> rsp = new Response<>();
        rsp.setCode(ReturnCode.SUCCESS);
        rsp.setMsg(message);
        rsp.setResult(result);
        return rsp;
    }

    /**
     * 创建一个状态为指定返回码的响应
     *
     * @param code 返回码
     * @param args 消息格式化参数
     * @param <T>
     * @return 响应对象
     */
    public static <T> Response<T> withCode(ReturnCode code, Object... args) {
        Response<T> rsp = new Response<>();
        rsp.setCode(code, args);
        return rsp;
    }

    /**
     * 创建一个返回码为404的响应，表示记录不存在
     *
     * @param message 错误消息
     * @return 404 响应对象
     */
    public static <T> Response<T> notFound(String message) {
        Response<T> rsp = new Response<>();
        rsp.setCode(ReturnCode.NOT_FOUND);
        rsp.setMsg(message);
        return rsp;
    }

    /**
     * 创建一个返回码为400的响应，表示请求格式有误
     *
     * @param message 错误消息
     * @return 响应对象
     */
    public static <T> Response<T> badRequest(String message) {
        Response<T> rsp = new Response<>();
        rsp.setCode(ReturnCode.BAD_REQUEST);
        rsp.setMsg(message);
        return rsp;
    }

    /**
     * 创建一个状态为指定错误返回码的响应
     *
     * @param code   错误返回码
     * @param errMsg 错误消息
     * @return 错误响应对象
     */
    public static <T> Response<T> error(ReturnCode code, String errMsg) {
        Response<T> rsp = withCode(code);
        rsp.setError(errMsg);
        return rsp;
    }

    /**
     * a new Dcz rsp.
     *
     * @author wuiang
     */
    public Response() {

    }

    /**
     * a new Dcz rsp.
     *
     * @param result the result
     * @author wuiang
     */
    public Response(T result) {
        this.result = result;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param retCode the ret code
     */
    public void setStatus(String retCode) {
        this.status = retCode;
    }

    /**
     * Gets msg.
     *
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets msg.
     *
     * @param retDesc the ret desc
     */
    public void setMsg(String retDesc) {
        this.msg = retDesc;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Gets error.
     *
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * Sets error.
     *
     * @param error the error
     */
    public void setError(String error) {
        this.error = error;
    }

    public void setCode(ReturnCode code, Object... args) {
        Objects.requireNonNull(code, "code must not be null");
        this.setStatus(code.value());
        if (args != null && args.length > 0) {
            this.setMsg(String.format(code.getMessage(), args));
        } else {
            this.setMsg(code.getMessage());
        }
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public T getResult() {
        return result;
    }

    /**
     * Sets result.
     *
     * @param result the result
     */
    public void setResult(T result) {
        this.result = result;
    }

    /**
     * To string string.
     *
     * @return the string
     * @author wuiang
     */
    @Override
    public String toString() {
        return "Response{"
                + "status='" + status + '\''
                + ", msg='" + msg + '\''
                + ", error='" + error + '\''
                + ", isSuccess='" + isSuccess + '\''
                + ", result=" + result
                + ", timestamp=" + timestamp
                + '}';
    }

    public boolean isSuccess() {
        return ReturnCode.SUCCESS.value().equals(status) || isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
