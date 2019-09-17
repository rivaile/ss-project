package com.rainbow.web.controller;

import com.rainbow.domain.SysUser;
import com.rainbow.dto.User;
import com.rainbow.req.SysUserReq;
import com.rainbow.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;


    @GetMapping
    public User getUser() {
        return new User();
    }

    @ApiOperation(value = "添加用户")
    @PostMapping
    public Integer addSysUser(@RequestBody SysUserReq sysUserReq) {

        logger.info("--create--" + sysUserReq);

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserReq, sysUser);
        sysUser.setOperator("system");
        sysUser.setOperateIp("192.168.1.1");
        sysUser.setOperateTime(new Date());
        return sysUserService.addSysUser(sysUser);
//        return 1;
    }

}
