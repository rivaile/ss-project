//package com.rainbow.app.server;
//
//import com.rainbow.app.authentication.openid.OpenIdAuthenticationSecurityConfig;
//import com.rainbow.core.authentication.FormAuthenticationConfig;
//import com.rainbow.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
//import com.rainbow.core.authorize.AuthorizeConfigManager;
//import com.rainbow.core.validate.code.ValidateCodeSecurityConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.social.security.SpringSocialConfigurer;
//
///**
// * @author denglin
// * @version V1.0
// * @Description:
// * @ClassName: AppResourceServerConfig
// * @date 2018/9/20 18:26
// */
//@Configuration
//@EnableResourceServer
//public class AppResourceServerConfig extends ResourceServerConfigurerAdapter {
//
//    @Autowired
//    private FormAuthenticationConfig formAuthenticationConfig;
//
//    @Autowired
//    private ValidateCodeSecurityConfig validateCodeSecurityConfig;
//
//    @Autowired
//    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
//
//    @Autowired
//    private SpringSocialConfigurer socialSecurityConfig;
//
//    @Autowired
//    private OpenIdAuthenticationSecurityConfig openIdAuthenticationSecurityConfig;
//
//    @Autowired
//    private AuthorizeConfigManager authorizeConfigManager;
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//
//        formAuthenticationConfig.configure(http);
//
//        http.apply(validateCodeSecurityConfig)
//                .and().apply(smsCodeAuthenticationSecurityConfig)
//                .and().apply(socialSecurityConfig)
//                .and().apply(openIdAuthenticationSecurityConfig)
//                .and().csrf().disable();
//
//        authorizeConfigManager.config(http.authorizeRequests());
//
//    }
//
//}
