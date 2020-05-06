package com.rainbow.web.filter;

import com.google.common.base.Splitter;
import com.google.common.collect.Sets;
import com.rainbow.business.system.service.impl.SystemCoreService;
import com.rainbow.common.RequestHolder;
import com.rainbow.domain.SystemUserDO;
import com.rainbow.domain.vo.RestResult;
import com.rainbow.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Slf4j
public class AuthControlFilter implements Filter {

    @Value("${sso.exclusion-urls}")
    private String exclusionUrls;

    @Autowired
    private SystemCoreService systemCoreService;

    @Autowired
    private JsonMapper JsonMapper;

    private static Set<String> exclusionUrlSet = Sets.newConcurrentHashSet();

    private final static String noAuthUrl = "/sys/user/noAuth.page";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        List<String> exclusionUrlList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(exclusionUrls);
        exclusionUrlSet = Sets.newConcurrentHashSet(exclusionUrlList);
        exclusionUrlSet.add(noAuthUrl);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String servletPath = request.getServletPath();
        Map requestMap = request.getParameterMap();

        if (exclusionUrlSet.contains(servletPath)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        SystemUserDO user = RequestHolder.getCurrentUser();
        if (user == null) {
            log.info("someone visit {}, but no login, parameter:{}", servletPath);
            log.info("{} visit {}, but no login, parameter:{}", JsonMapper.obj2String(user), servletPath, JsonMapper.obj2String(requestMap));
            RestResult result = RestResult.badRequest("没有访问权限，如需要访问，请联系管理员");
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(JsonMapper.obj2String(result));
            return;
        }

        if (!systemCoreService.hasUrlAuth(servletPath)) {
            log.info("{} visit {}, but no login, parameter:{}", JsonMapper.obj2String(user), servletPath, JsonMapper.obj2String(requestMap));
            RestResult result = RestResult.badRequest("没有访问权限，如需要访问，请联系管理员");
            response.setHeader("Content-Type", "application/json");
            response.getWriter().print(JsonMapper.obj2String(result));
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
        return;
    }

    @Override
    public void destroy() {
    }
}
