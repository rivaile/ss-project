package com.rainbow.business.system.controller;

import com.rainbow.enums.ReturnCode;
import com.rainbow.exception.BusinessException;
import com.rainbow.exception.UserNotExistException;
import com.rainbow.vo.RestResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

/**
 * controller 的异常处理...默认处理机制,client 返回 json , web 返回页面..
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public RestResult handleBusinessException(BusinessException exception) {
        return RestResult.error(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestResult handleException(Exception ex) {
        return RestResult.error(ReturnCode.BAD_REQUEST, ex.getMessage());
    }


//    @ExceptionHandler(UserNotExistException.class)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public Map<String, Object> handleUserNotExistException(UserNotExistException ex) {
//        Map<String, Object> result = new HashMap<>();
//        result.put("id", ex.getId());
//        result.put("message", ex.getMessage());
//        return result;
//    }
}
