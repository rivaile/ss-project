package com.rainbow.service;

import com.rainbow.dao.mapper.SysUserMapper;
import com.rainbow.domain.SysUser;
import com.rainbow.enums.ReturnCode;
import com.rainbow.vo.Response;
import com.rainbow.vo.SysUserReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SysUserService extends BaseService<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Response<Object> addSysUser(SysUserReq userReq) {
        //todo: 电话邮箱校验

        SysUser user = new SysUser();
        BeanUtils.copyProperties(userReq, user);
        user.setPassword(passwordEncoder.encode("123456"));
        user.setOperator("system");
        user.setOperateIp("192.168.1.1");
        user.setOperateTime(new Date());

        if (save(user))
            return Response.success("注册成功");
        else
            return Response.error(ReturnCode.AUTH_SERVER_1010001,"dsds");
        // todo: 操作日志 > 可以使用aop实现
    }

    public void updateSysUser(SysUser sysUser) {
//        sysUserMapper.update(user);
    }


}
