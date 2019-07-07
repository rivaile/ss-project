package com.rainbow.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author denglin
 * @version V1.0
 * @Description: 校验码处理器，封装不同校验码的处理逻辑
 * @ClassName: ValidateCodeProcessor
 * @date 2018/9/17 17:55
 */
public interface ValidateCodeProcessor {
    /**
     * 创建校验码
     *
     * @param request
     * @throws Exception
     */
    void create(ServletWebRequest request) throws Exception;

    /**
     * 校验验证码
     *
     * @param servletWebRequest
     * @throws Exception
     */
    void validate(ServletWebRequest servletWebRequest);
}
