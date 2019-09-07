package com.rainbow.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author denglin
 * @version V1.0
 * @Description: filter 只能拿到原始请求的信息, Interceptor 可以拿到原始请求的信息,和方法名参数, aspect 可以拿到方法名,拿到方法参数...
 * @ClassName: TimeAspect
 * @date 2018/9/17 11:19
 */
@Aspect
@Component
public class TimeAspect {

    @Around("execution(* com.rainbow.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println("time aspect start");

        Object[] args = pjp.getArgs();
        for (Object arg : args) {
            System.out.println("arg is " + arg);
        }

        long start = new Date().getTime();

        Object object = pjp.proceed();

        System.out.println("time aspect 耗时:" + (new Date().getTime() - start));

        System.out.println("time aspect end");

        return object;
    }

}
