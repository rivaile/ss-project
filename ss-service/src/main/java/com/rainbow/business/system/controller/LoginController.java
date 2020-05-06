package com.rainbow.business.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rainbow.domain.SystemUserDO;
import com.rainbow.business.system.service.impl.SystemUserService;
import com.rainbow.domain.vo.RestResult;
import com.rainbow.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-10-12 17:48
 */
@RestController
public class LoginController {

    @Autowired
    private SystemUserService systemUserService;

    @PostMapping("/login")
    public RestResult login(String username, String password) {

        SystemUserDO user = systemUserService.getOne(new QueryWrapper<SystemUserDO>().lambda()
                .eq(SystemUserDO::getUsername, username)
                .or()
                .eq(SystemUserDO::getUsername, username)
        );

        String errorMsg = "";

        if (StringUtils.isBlank(username)) {
            errorMsg = "用户名不可以为空";
        } else if (StringUtils.isBlank(password)) {
            errorMsg = "密码不可以为空";
        } else if (user == null) {
            errorMsg = "查询不到指定的用户";
        } else if (!user.getPassword().equals(MD5Util.encrypt(password))) {
            errorMsg = "用户名或密码错误";
        } else if (user.getStatus() != 1) {
            errorMsg = "用户已被冻结，请联系管理员";
        } else {
            //todo ...
            return RestResult.success();
        }
        return RestResult.badRequest(errorMsg);
    }
}
