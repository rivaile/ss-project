package com.rainbow.web.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author denglin
 * @version V1.0
 * @Description: Interceptor 可以拿到类名,方法名.拿不到参数.
 * @ClassName: TimeInterceptor
 * @date 2018/9/15 17:47
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("preHandle");

        System.out.println(((HandlerMethod) handler).getBean().getClass().getName());
        System.out.println(((HandlerMethod) handler).getMethod().getName());

        request.setAttribute("startTime", new Date().getTime());
        return true;
    }

    /**
     * 只要方法没有抛出异常,就会被调用...
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
        Long start = (Long) request.getAttribute("startTime");
        System.out.println("time interceptor 耗时:" + (new Date().getTime() - start));

    }

    /**
     * 只要preHandle为true,就会被调用!
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("afterCompletion");
        Long start = (Long) request.getAttribute("startTime");
        System.out.println("time interceptor 耗时:" + (new Date().getTime() - start));
        System.out.println("ex is " + ex);

    }

}
