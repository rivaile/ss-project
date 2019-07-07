package com.rainbow.core.social.support;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author denglin
 * @version V1.0
 * @Description: SocialAuthenticationFilter后处理器，用于在不同环境下个性化社交登录的配置
 * @ClassName: SocialAuthenticationFilterPostProcessor
 * @date 2018/9/19 18:06
 */
public interface SocialAuthenticationFilterPostProcessor {

    void process(SocialAuthenticationFilter socialAuthenticationFilter);

}
