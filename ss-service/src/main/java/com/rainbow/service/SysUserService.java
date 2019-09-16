package com.rainbow.service;

import com.rainbow.dao.SysUserMapper;
import com.rainbow.domain.SysUser;
import com.rainbow.req.SysUserReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    public Integer addSysUser(SysUser user) {
        return sysUserMapper.insert(user);
    }

}
