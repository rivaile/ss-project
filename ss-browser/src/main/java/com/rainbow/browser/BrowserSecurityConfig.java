package com.rainbow.browser;

import com.rainbow.browser.authentication.BrowserAuthenctiationFailureHandler;
import com.rainbow.browser.authentication.BrowserAuthenticationSuccessHandler;
import com.rainbow.core.authentication.FormAuthenticationConfig;
import com.rainbow.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.rainbow.core.authorize.AuthorizeConfigManager;
import com.rainbow.core.properties.SecurityProperties;
import com.rainbow.core.validate.code.ValidateCodeFilter;
import com.rainbow.core.validate.code.ValidateCodeSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * @author denglin
 * @version V1.0
 * @Description: 浏览器环境下安全配置主类
 * @ClassName: BrowserSecurityConfig
 * @date 2018/9/17 13:29
 */
@Configuration
@EnableWebSecurity
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private BrowserAuthenticationSuccessHandler successHandler;

    @Autowired
    private BrowserAuthenctiationFailureHandler failureHandler;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private SpringSocialConfigurer springSocialConfigurer;

    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;

    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;

    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;


    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;

    @Autowired
    private FormAuthenticationConfig formAuthenticationConfig;

    @Autowired
    private ValidateCodeSecurityConfig validateCodeSecurityConfig;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().permitAll();

//        formAuthenticationConfig.configure(http);
//
//        http.apply(validateCodeSecurityConfig)
//                .and()
//                    .apply(smsCodeAuthenticationSecurityConfig)
//                .and()
//                    .apply(springSocialConfigurer)
//                .and()
//                .rememberMe()
//                .tokenRepository(persistentTokenRepository())
//                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
//                .userDetailsService(userDetailsService)
//                .and()
//                .sessionManagement()
//                .invalidSessionStrategy(invalidSessionStrategy)
//                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
////                .maxSessionsPreventsLogin(true)
//                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
//                //并发登录
//                .expiredSessionStrategy(sessionInformationExpiredStrategy).and().and()
//                .logout()
//                .logoutUrl("/signOut")
////                    .logoutSuccessUrl("/imooc-logout.html")
//                .logoutSuccessHandler(logoutSuccessHandler)//url and handler 只能生效一个
//                .deleteCookies("JSESSIONID").and()
//                .csrf().disable();
//
//        authorizeConfigManager.config(http.authorizeRequests());
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//      tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }


}
