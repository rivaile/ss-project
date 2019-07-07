package com.rainbow.core.validate.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author denglin
 * @version V1.0
 * @Description: 默认的短信验证码发送器
 * @ClassName: DefaultSmsCodeSender
 * @date 2018/9/18 17:26
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void send(String mobile, String code) {
        logger.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
        logger.info("向手机" + mobile + "发送短信验证码" + code);
    }
}
