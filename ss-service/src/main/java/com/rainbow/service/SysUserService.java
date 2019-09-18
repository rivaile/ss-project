package com.rainbow.service;

import com.rainbow.dao.mapper.SysUserMapper;
import com.rainbow.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysUserService{

    @Autowired
    private SysUserMapper sysUserMapper;

    public Integer addSysUser(SysUser user) {
        user.setOperator("system");
        user.setOperateIp("192.168.1.1");
        user.setOperateTime(new Date());
        return sysUserMapper.insert(user);
    }

    public void updateSysUser(SysUser sysUser) {
//        sysUserMapper.update(user);
    }
}
