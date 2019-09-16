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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;

    @ApiOperation(value = "添加用户")
    @PostMapping
    public Integer addSysUser(@RequestBody User sysUserReq) {

        logger.info("--create--" + sysUserReq);

//        SysUser sysUser = new SysUser();
//        BeanUtils.copyProperties(sysUserReq, sysUser);
//        return sysUserService.addSysUser(sysUser);
        return 1;
    }

}
