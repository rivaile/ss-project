package com.rainbow.browser.session;

import com.rainbow.core.properties.SecurityProperties;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author denglin
 * @version V1.0
 * @Description: 并发登录导致session失效时，默认的处理策略
 * @ClassName: BrowserExpiredSessionStrategy
 * @date 2018/9/20 16:19
 */
public class BrowserExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

    public BrowserExpiredSessionStrategy(SecurityProperties securityPropertie) {
        super(securityPropertie);
    }

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        onSessionInvalid(event.getRequest(), event.getResponse());
    }

    @Override
    protected boolean isConcurrency() {
        return true;
    }
}
