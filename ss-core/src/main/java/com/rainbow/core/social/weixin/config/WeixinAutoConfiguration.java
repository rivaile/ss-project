package com.rainbow.core.social.weixin.config;

import com.rainbow.core.properties.SecurityProperties;
import com.rainbow.core.properties.WeixinProperties;
import com.rainbow.core.social.view.ConnectView;
import com.rainbow.core.social.weixin.connect.WeixinConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * @author denglin
 * @version V1.0
 * @Description: 微信登录配置
 * @ClassName: WeixinAutoConfiguration
 * @date 2018/9/19 15:50
 */
@Configuration
@ConditionalOnProperty(prefix = "security.social.weixin", name = "app-id")
public class WeixinAutoConfiguration extends SocialConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer configurer,
                                       Environment environment) {
        configurer.addConnectionFactory(createConnectionFactory());
    }

    protected ConnectionFactory<?> createConnectionFactory() {
        WeixinProperties weixinConfig = securityProperties.getSocial().getWeixin();
        return new WeixinConnectionFactory(weixinConfig.getProviderId(), weixinConfig.getAppId(),
                weixinConfig.getAppSecret());
    }

    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "weixinConnectedView")
    //绑定以后的视图-->connect/weixinConnected ,解绑以后的视图(connect/weixinConnect) 解绑是delete请求
    public View weixinConnectedView() {
        return new ConnectView();
    }

}
