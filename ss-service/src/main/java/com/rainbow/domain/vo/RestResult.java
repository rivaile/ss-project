package com.rainbow.domain.vo;

import com.rainbow.enums.ReturnCode;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "通用响应体")
public class RestResult<T> implements Serializable {
    /**
     * 返回错误码，具体的编码规范参见"系统设计"
     */
    @ApiModelProperty(value = "返回编码", required = true)
    private String status;
    @ApiModelProperty(value = "返回消息", required = true)
    private String message;
    @ApiModelProperty(value = "错误详情", required = true)
    private String error;
    @ApiModelProperty(value = "返回内容", required = true)
    private T data;
    @ApiModelProperty(value = "返回请求状态", required = true)
    private boolean success;
    @ApiModelProperty(value = "返回时间", required = true)
    private final Date timestamp = new Date();

    /**
     * 创建一个状态为成功的响应
     *
     * @param message 响应消息
     * @return 响应对象
     */
    public static <T> RestResult<T> success(String message) {
        RestResult<T> rsp = new RestResult<>();
        rsp.setCode(ReturnCode.SUCCESS);
        rsp.setMessage(message);
        return rsp;
    }

    public static <T> RestResult<T> success() {
        RestResult<T> rsp = new RestResult<>();
        rsp.setCode(ReturnCode.SUCCESS);
        rsp.setMessage("操作成功!");
        return rsp;
    }

    /**
     * 创建一个状态为成功的响应
     *
     * @param result 返回内容
     */
    public static <T> RestResult<T> success(T result) {
        RestResult<T> rsp = new RestResult<>();
        rsp.setCode(ReturnCode.SUCCESS);
        rsp.setData(result);
        return rsp;
    }

    /**
     * 创建一个状态为成功的响应
     * success 字段为true
     * status 为不同的业务编码
     *
     * @param result 返回内容
     */
    public static <T> RestResult<T> success(ReturnCode code, T result) {
        RestResult<T> rsp = new RestResult<>();
        rsp.setSuccess(true);
        rsp.setCode(code);
        rsp.setData(result);
        return rsp;
    }

    /**
     * 创建一个状态为成功的响应
     *
     * @param result  返回内容
     * @param message 响应消息
     */
    public static <T> RestResult<T> success(T result, String message) {
        RestResult<T> rsp = new RestResult<>();
        rsp.setCode(ReturnCode.SUCCESS);
        rsp.setMessage(message);
        rsp.setData(result);
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
    public static <T> RestResult<T> withCode(ReturnCode code, Object... args) {
        RestResult<T> rsp = new RestResult<>();
        rsp.setCode(code, args);
        return rsp;
    }

    /**
     * 创建一个返回码为404的响应，表示记录不存在
     *
     * @param message 错误消息
     * @return 404 响应对象
     */
    public static <T> RestResult<T> notFound(String message) {
        RestResult<T> rsp = new RestResult<>();
        rsp.setCode(ReturnCode.NOT_FOUND);
        rsp.setMessage(message);
        return rsp;
    }

    /**
     * 创建一个返回码为400的响应，表示请求格式有误
     *
     * @param message 错误消息
     * @return 响应对象
     */
    public static <T> RestResult<T> badRequest(String message) {
        RestResult<T> rsp = new RestResult<>();
        rsp.setCode(ReturnCode.BAD_REQUEST);
        rsp.setMessage(message);
        return rsp;
    }

    /**
     * 创建一个状态为指定错误返回码的响应
     *
     * @param code   错误返回码
     * @param errMsg 错误消息
     * @return 错误响应对象
     */
    public static <T> RestResult<T> error(ReturnCode code, String errMsg) {
        RestResult<T> rsp = withCode(code);
        rsp.setError(errMsg);
        return rsp;
    }

    /**
     * a new Dcz rsp.
     *
     * @author wuiang
     */
    public RestResult() {

    }

    /**
     * a new Dcz rsp.
     *
     * @param result the data
     * @author wuiang
     */
    public RestResult(T result) {
        this.data = result;
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
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param retDesc the ret desc
     */
    public void setMessage(String retDesc) {
        this.message = retDesc;
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
            this.setMessage(String.format(code.getMessage(), args));
        } else {
            this.setMessage(code.getMessage());
        }
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(T data) {
        this.data = data;
    }


    public boolean isSuccess() {
        return ReturnCode.SUCCESS.value().equals(status) || success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * To string string.
     *
     * @return the string
     * @author wuiang
     */
    @Override
    public String toString() {
        return "RestResult{"
                + "status='" + status + '\''
                + ", message='" + message + '\''
                + ", error='" + error + '\''
                + ", success='" + success + '\''
                + ", data=" + data
                + ", timestamp=" + timestamp
                + '}';
    }

}
