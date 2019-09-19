package com.rainbow.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rainbow.dao.mapper.SysUserMapper;
import com.rainbow.domain.SysUser;
import com.rainbow.req.SysUserReq;
import com.rainbow.service.SysUserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserMapper userMapper;

    @ApiOperation(value = "添加用户")
    @PostMapping
    public Integer addSysUser(@Valid @RequestBody SysUserReq sysUserReq) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserReq, sysUser);
//        return sysUserService.addSysUser(sysUser);
        return  1;
    }

    @ApiOperation(value = "修改用户")
    @PutMapping
    public void updateSysUser(@RequestBody SysUserReq sysUserReq) {
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserReq, sysUser);
//        return sysUserService.updateSysUser(sysUser);
    }

    @GetMapping
    public IPage<SysUser> getUserList() {
        IPage<SysUser> sysUserIPage = userMapper.selectPageVo(new Page());
        return sysUserIPage;
    }
}
