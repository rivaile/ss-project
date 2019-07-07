package com.rainbow.app;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: AuthenticationManagerConfig
 * @date 2018/9/23 17:08
 */
@Configuration
@ConditionalOnMissingBean({AuthenticationManager.class})
@Order(0)
public class AuthenticationManagerConfig {
    @Bean
    @Primary
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
