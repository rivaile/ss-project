package com.rainbow.app.social;

import com.rainbow.core.properties.SecurityConstants;
import com.rainbow.core.social.support.SsSpringSocialConfigurer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: SpringSocialConfigurerPostProcessor
 * @date 2018/9/23 16:03
 */
@Component
public class SpringSocialConfigurerPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (StringUtils.equals(beanName, "springSocialConfigurer")) {//SocialConfig---->springSocialConfigurer
            SsSpringSocialConfigurer config = (SsSpringSocialConfigurer) bean;
            config.signupUrl(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL);
            return config;
        }
        return bean;
    }

}
