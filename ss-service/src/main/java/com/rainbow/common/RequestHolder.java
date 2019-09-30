package com.rainbow.common;

import com.rainbow.domain.SysUser;

public class RequestHolder {

    private static final ThreadLocal<SysUser> userHolder = new ThreadLocal<SysUser>();


    public static void add(SysUser sysUser) {
        userHolder.set(sysUser);
    }

    public static SysUser getCurrentUser() {
        return userHolder.get();
    }

    public static void remove() {
        userHolder.remove();
    }
}
