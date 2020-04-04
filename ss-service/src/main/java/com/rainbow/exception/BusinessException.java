package com.rainbow.exception;

import com.rainbow.enums.ReturnCode;

import java.util.Objects;

/**
 * @author lin.deng
 * @version 1.0
 * @Description: 业务异常
 * @since 2017年12月28日
 */
public class BusinessException extends RuntimeException {

    private String detail;

    private ReturnCode code;

    /**
     * @param e      Exception
     * @param code   Return code.
     * @param detail error desc.
     */
    public BusinessException(Throwable e, ReturnCode code, String detail) {
        super(e);
        if (code == null) {
            throw new IllegalArgumentException();
        }
        this.code = code;
        this.detail = detail;
    }

    /**
     * @param code Return code.
     */
    public BusinessException(ReturnCode code) {
        if (code == null) {
            throw new IllegalArgumentException();
        }
        this.code = code;
        this.detail = code.getMessage();
    }

    public BusinessException(String status, Object... message) {
        Objects.requireNonNull(status, "status must not be null");
        Objects.requireNonNull(message, "message must not be null");
        this.code = ReturnCode.valueOf(status);
        this.detail = message.toString();
    }

    public BusinessException(ReturnCode code, Object... args) {
        Objects.requireNonNull(code, "code must not be null");
        this.code = code;
        this.detail = String.format(code.getMessage(), args);
    }

    @Override
    public String getMessage() {
        return this.code.getMessage();
    }

    public ReturnCode getCode() {
        return this.code;
    }

    public String getDetail() {
        return detail;
    }
}
