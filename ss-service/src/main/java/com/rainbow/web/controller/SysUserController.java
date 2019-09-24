package com.rainbow.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rainbow.dao.mapper.SysUserMapper;
import com.rainbow.domain.SysUser;
import com.rainbow.service.ISysUserService;
import com.rainbow.vo.Response;
import com.rainbow.vo.SysUserReq;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysUserMapper userMapper;

    @ApiOperation(value = "添加用户")
    @PostMapping
    public Response<String> addSysUser(@Valid @RequestBody SysUserReq req) {
        return sysUserService.addSysUser(req);
    }

    @ApiOperation(value = "修改用户")
    @PutMapping
    public Response updateSysUser(@RequestBody SysUserReq sysUserReq) {
        return sysUserService.updateUser(sysUserReq);
    }

    @GetMapping
    public Response<IPage<SysUser>> getUserList() {
        return sysUserService.getSysUser();
    }

}
