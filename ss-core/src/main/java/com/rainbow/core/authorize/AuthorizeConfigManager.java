package com.rainbow.core.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author denglin
 * @version V1.0
 * @Description: 授权信息管理器--->用于收集系统中所有 AuthorizeConfigProvider 并加载其配置
 * @ClassName: AuthorizeConfigManager
 * @date 2018/9/25 11:22
 */
public interface AuthorizeConfigManager {

    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);

}
