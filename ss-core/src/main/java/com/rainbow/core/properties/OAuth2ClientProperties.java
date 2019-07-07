package com.rainbow.core.properties;

/**
 * @author denglin
 * @version V1.0
 * @Description: 认证服务器注册的第三方应用配置项
 * @ClassName: OAuth2ClientProperties
 * @date 2018/9/23 16:23
 */
public class OAuth2ClientProperties {
    /**
     * 第三方应用appId
     */
    private String clientId;
    /**
     * 第三方应用appSecret
     */
    private String clientSecret;
    /**
     * 针对此应用发出的token的有效时间,如果是0是不会过期的
     */
    private int accessTokenValidateSeconds = 7200;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getAccessTokenValidateSeconds() {
        return accessTokenValidateSeconds;
    }

    public void setAccessTokenValidateSeconds(int accessTokenValidateSeconds) {
        this.accessTokenValidateSeconds = accessTokenValidateSeconds;
    }


}
