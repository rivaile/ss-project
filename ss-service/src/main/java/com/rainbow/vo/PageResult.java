package com.rainbow.vo;

import com.rainbow.enums.ReturnCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * 通用分页响应（同步）报文结构
 *
 * @author deng.lin
 * @version 1.0
 * @created 30-七月-2020 16:15:24
 */
@ApiModel(value = "分页响应实体")
public class PageResult<T> implements Serializable {
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
    private List<T> data;
    @ApiModelProperty(value = "返回时间", required = true)
    private final Date timestamp = new Date();
    @ApiModelProperty(value = "返回请求状态", required = true)
    private boolean success;
    @ApiModelProperty(value = "总条数", notes = "总条数")
    private Long total;
    @ApiModelProperty(value = "总页数", notes = "总页数")
    private Long pages;

    @ApiModelProperty(value = "当前页", notes = "当前页")
    private Long current;

    /**
     * 创建一个状态为成功的响应
     *
     * @param message 响应消息
     * @return 响应对象
     */
    public static <T> PageResult<T> success(String message) {
        PageResult<T> rsp = new PageResult<>();
        rsp.setCode(ReturnCode.SUCCESS);
        rsp.setMsg(message);
        return rsp;
    }

    /**
     * 创建一个状态为成功的响应
     *
     * @param result 返回内容
     */
    public static <T> PageResult<T> success(List<T> result) {
        PageResult<T> rsp = new PageResult<>();
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
    public static <T> PageResult<T> success(ReturnCode code, List<T> result) {
        PageResult<T> rsp = new PageResult<>();
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
    public static <T> PageResult<T> success(List<T> result, String message) {
        PageResult<T> rsp = new PageResult<>();
        rsp.setCode(ReturnCode.SUCCESS);
        rsp.setMsg(message);
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
    public static <T> PageResult<T> withCode(ReturnCode code, Object... args) {
        PageResult<T> rsp = new PageResult<>();
        rsp.setCode(code, args);
        return rsp;
    }

    /**
     * 创建一个返回码为404的响应，表示记录不存在
     *
     * @param message 错误消息
     * @return 404 响应对象
     */
    public static <T> PageResult<T> notFound(String message) {
        PageResult<T> rsp = new PageResult<>();
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
    public static <T> PageResult<T> badRequest(String message) {
        PageResult<T> rsp = new PageResult<>();
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
    public static <T> PageResult<T> error(ReturnCode code, String errMsg) {
        PageResult<T> rsp = withCode(code);
        rsp.setError(errMsg);
        return rsp;
    }

    /**
     * a new  rsp.
     *
     * @author wuiang
     */
    public PageResult() {
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
     * Gets data.
     *
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(List<T> data) {
        this.data = data;
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
                + ", msg='" + msg + '\''
                + ", error='" + error + '\''
                + ", success='" + success + '\''
                + ", data=" + data
                + ", timestamp=" + timestamp
                + '}';
    }

    public boolean isSuccess() {
        return ReturnCode.SUCCESS.value().equals(status) || success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Long getPages() {
        return pages;
    }

    public void setPages(Long pages) {
        this.pages = pages;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }
}
