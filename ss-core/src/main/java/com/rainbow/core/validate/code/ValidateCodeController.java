package com.rainbow.core.validate.code;

import com.rainbow.core.properties.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author denglin
 * @version V1.0
 * @Description: 生成校验码的请求处理器
 * @ClassName: ValidateCodeController
 * @date 2018/9/17 14:44
 */
@RestController
public class ValidateCodeController {

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response,
                           @PathVariable String type) throws Exception {
        validateCodeProcessorHolder
                .findValidateCodeProcessor(type)
                .create(new ServletWebRequest(request, response));
    }


//    @GetMapping("/code/image")
//    public String createCode() {
//        return "code";
//    }
}
