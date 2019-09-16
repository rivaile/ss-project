package com.rainbow.web.filter;

import javax.servlet.*;
import java.io.IOException;
import java.util.Date;

/**
 * @author denglin
 * @version V1.0
 * @Description: @Component 直接生效, 如果没有 @Component 注解 需要在webconfig 中配置.
 * @ClassName: TimeFilter
 * @date 2018/9/15 17:36
 */
//@Component
public class TimeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("time filter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        System.out.println("time filter start");
        long start = new Date().getTime();
        filterChain.doFilter(servletRequest, servletResponse);
        System.out.println("time filter 耗时:" + (new Date().getTime() - start));
        System.out.println("time filter finish");
    }

    @Override
    public void destroy() {
        System.out.println("time filter destroy");
    }
}
