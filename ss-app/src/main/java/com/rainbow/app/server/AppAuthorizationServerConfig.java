//package com.rainbow.app.server;
//
//import com.rainbow.core.properties.SecurityProperties;
//import org.apache.commons.lang.ArrayUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author denglin
// * @version V1.0
// * @Description: 认证服务器配置
// * @ClassName: AppAuthorizationServerConfig
// * @date 2018/9/20 17:28
// */
//@Configuration
//@EnableAuthorizationServer
//public class AppAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private TokenStore tokenStore;
//
//    @Autowired(required = false)
//    private JwtAccessTokenConverter jwtAccessTokenConverter;
//
//    @Autowired(required = false)
//    private TokenEnhancer jwtTokenEnhancer;
//
//    @Autowired
//    private SecurityProperties securityProperties;
//
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//
//        endpoints.tokenStore(tokenStore)
//                .authenticationManager(authenticationManager)
//                .userDetailsService(userDetailsService);
//
//        if (jwtAccessTokenConverter != null && jwtTokenEnhancer != null) {
//            TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
//            List<TokenEnhancer> enhancers = new ArrayList<>();
//            enhancers.add(jwtTokenEnhancer);
//            enhancers.add(jwtAccessTokenConverter);
//            enhancerChain.setTokenEnhancers(enhancers);
//            endpoints.tokenEnhancer(enhancerChain).accessTokenConverter(jwtAccessTokenConverter);
//        }
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception { //指定这个之后配置文件里面就不起作用了!
//
//        clients.inMemory().withClient("imooc")
//                .secret("imoocecret")
//                .authorizedGrantTypes("refresh_token", "authorization_code", "password")//授权方式
//                .scopes("all", "read", "write");
//
////        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
////        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
////            for (OAuth2ClientProperties client : securityProperties.getOauth2().getClients()) {
////                builder.withClient(client.getClientId())
////                        .secret(client.getClientSecret())
////                        .authorizedGrantTypes("refresh_token", "authorization_code", "password")
////                        .accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
////                        .refreshTokenValiditySeconds(2592000)
////                        .scopes("all");
////            }
////        }
//    }
//}
