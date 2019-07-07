package com.rainbow.web;

import com.rainbow.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: ExceptionHandlerAdvice--restful 错误处理!!!------>BasicErrorController
 * @date 2018/9/15 17:16
 */
@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(UserNotExistException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleUserNotExistException(UserNotExistException ex) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", ex.getId());
        result.put("message", ex.getMessage());
        return result;
    }

//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView exception(Exception exception, WebRequest request) {
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("errorMessage", exception.getMessage());
//        return modelAndView;
//    }
//
//    @ModelAttribute
//    public void addAttribute(Model model) {
//        model.addAttribute("msg","额外消息");
//    }
//
//    @InitBinder
//    public void initBinder(WebDataBinder webDataBinder){
//        webDataBinder.setDisallowedFields("id");
//    }

}
