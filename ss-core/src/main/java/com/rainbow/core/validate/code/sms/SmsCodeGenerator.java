package com.rainbow.core.validate.code.sms;

import com.rainbow.core.properties.SecurityProperties;
import com.rainbow.core.validate.code.ValidateCode;
import com.rainbow.core.validate.code.ValidateCodeGenerator;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author denglin
 * @version V1.0
 * @Description: 短信验证码生成器
 * @ClassName: SmsCodeGenerator
 * @date 2018/9/18 17:29
 */
@Component("smsValidateCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generate(ServletWebRequest request) {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
    }

}
