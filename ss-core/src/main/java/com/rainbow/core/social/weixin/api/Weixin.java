package com.rainbow.core.social.weixin.api;

/**
 * @author denglin
 * @version V1.0
 * @Description: 微信API调用接口
 * @ClassName: Weixin
 * @date 2018/9/19 15:11
 */
public interface Weixin {

    WeixinUserInfo getUserInfo(String openId);

}
