package com.rainbow.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rainbow.dao.mapper.SysUserMapper;
import com.rainbow.domain.SysUser;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysUserService extends ServiceImpl<SysUserMapper, SysUser> {

    public Integer addSysUser(SysUser user) {

        user.setOperator("system");
        user.setOperateIp("192.168.1.1");
        user.setOperateTime(new Date());

        if (save(user)) {
            return 1;
        } else {
            return 0;
        }

    }

    public void updateSysUser(SysUser sysUser) {
//        sysUserMapper.update(user);
    }
}
