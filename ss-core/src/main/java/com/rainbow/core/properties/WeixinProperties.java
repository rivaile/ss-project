package com.rainbow.core.properties;


/**
 * @author denglin
 * @version V1.0
 * @Description: 微信登录配置项
 * @ClassName: WeixinProperties
 * @date 2018/9/19 9:35
 */
public class WeixinProperties {


    /**
     * Application id.
     */
    private String appId;

    /**
     * Application secret.
     */
    private String appSecret;


    /**
     * 第三方id，用来决定发起第三方登录的url，默认是 weixin。
     */
    private String providerId = "weixin";

    /**
     * @return the providerId
     */
    public String getProviderId() {
        return providerId;
    }

    /**
     * @param providerId the providerId to set
     */
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }


    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return this.appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }


}
