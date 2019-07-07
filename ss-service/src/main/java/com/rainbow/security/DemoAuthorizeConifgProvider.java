package com.rainbow.security;

import com.rainbow.core.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: DemoAuthorizeConifgProvider
 * @date 2018/9/25 17:26
 */
@Component
public class DemoAuthorizeConifgProvider implements AuthorizeConfigProvider {

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        //demo项目授权配置
        return false;
    }
}
