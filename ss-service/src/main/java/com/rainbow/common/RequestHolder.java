package com.rainbow.common;

import com.rainbow.domain.SystemUser;

import javax.servlet.http.HttpServletRequest;

public class RequestHolder {

    private static final ThreadLocal<SystemUser> userHolder = new ThreadLocal<SystemUser>();

    private static final ThreadLocal<HttpServletRequest> requestHolder = new ThreadLocal<HttpServletRequest>();

    public static void add(SystemUser sysUser) {
        userHolder.set(sysUser);
    }

    public static void add(HttpServletRequest request) {
        requestHolder.set(request);
    }

    public static SystemUser getCurrentUser() {
        return userHolder.get();
    }

    public static HttpServletRequest getCurrentRequest() {
        return requestHolder.get();
    }

    public static void remove() {
        userHolder.remove();
        requestHolder.remove();
    }
}
