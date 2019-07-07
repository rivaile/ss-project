package com.rainbow.browser.logout;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainbow.core.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author denglin
 * @version V1.0
 * @Description: 默认的退出成功处理器，如果设置了security.browser.signOutUrl，则跳到配置的地址上，如果没配置，则返回json格式的响应。
 * @ClassName: BrowserLogoutSuccessHandler
 * @date 2018/9/20 16:59
 */
public class BrowserLogoutSuccessHandler implements LogoutSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private String signOutSuccessUrl;

    private ObjectMapper objectMapper = new ObjectMapper();

    public BrowserLogoutSuccessHandler(String signOutSuccessUrl) {
        this.signOutSuccessUrl = signOutSuccessUrl;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        logger.info("退出成功");

        if (StringUtils.isBlank(signOutSuccessUrl)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
        } else {
            response.sendRedirect(signOutSuccessUrl);
        }
    }
}
