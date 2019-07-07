package com.rainbow.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: DemoConnectionSignUp
 * @date 2018/9/19 19:05
 */
//@Component
public class DemoConnectionSignUp implements ConnectionSignUp {
    @Override
    public String execute(Connection<?> connection) {
        //根据社交用户信息默认创建用户并返回用户唯一标识
        return connection.getDisplayName();
    }
}
