package com.rainbow.browser;

import com.rainbow.browser.logout.BrowserLogoutSuccessHandler;
import com.rainbow.browser.session.BrowserExpiredSessionStrategy;
import com.rainbow.browser.session.BrowserInvalidSessionStrategy;
import com.rainbow.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author denglin
 * @version V1.0
 * @Description: 浏览器环境下扩展点配置，配置在这里的bean，业务系统都可以通过声明同类型或同名的bean来覆盖安全模块默认的配置
 * @ClassName: BrowserSecurityBeanConfig
 * @date 2018/9/20 16:24
 */

@Configuration
public class BrowserSecurityBeanConfig {

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * session失效时的处理策略配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new BrowserInvalidSessionStrategy(securityProperties);
    }

    /**
     * 并发登录导致前一个session失效时的处理策略配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new BrowserExpiredSessionStrategy(securityProperties);
    }

    /**
     * 退出时的处理策略配置
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new BrowserLogoutSuccessHandler(securityProperties.getBrowser().getSignOutUrl());
    }

}
