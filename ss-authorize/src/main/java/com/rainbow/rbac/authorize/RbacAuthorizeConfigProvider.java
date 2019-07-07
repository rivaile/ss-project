package com.rainbow.rbac.authorize;

import com.rainbow.core.authorize.AuthorizeConfigProvider;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: RbacAuthorizeConfigProvider
 * @date 2018/9/25 11:30
 */
@Component
@Order(Integer.MAX_VALUE)
public class RbacAuthorizeConfigProvider implements AuthorizeConfigProvider {

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(HttpMethod.GET, "/fonts/**").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/**/*.html",
                        "/admin/me",
                        "/resource").authenticated()
                .anyRequest().access("@rbacService.hasPermission(request, authentication)");
        return true;
    }
}
