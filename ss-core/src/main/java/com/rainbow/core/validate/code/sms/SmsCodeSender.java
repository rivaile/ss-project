package com.rainbow.core.validate.code.sms;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: SmsCodeSender
 * @date 2018/9/18 17:22
 */
public interface SmsCodeSender {
    /**
     * @param mobile
     * @param code
     */
    void send(String mobile, String code);
}
