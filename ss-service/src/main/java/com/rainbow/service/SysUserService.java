package com.rainbow.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Preconditions;
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

        // todo: 操作日志 > 可以使用aop实现
        if (save(user))
            return Response.success("注册成功");
        else
            return Response.error(ReturnCode.AUTH_SERVER_1010006, "注册失败");
    }

    public void deleteByUser() {

    }


    public void updateUser(SysUserReq userReq) {
        // todo: 电话邮箱校验
        SysUser user = getById(userReq.getId());
        BeanUtils.copyProperties(userReq,
                Preconditions.checkNotNull(user, "待更新的用户不存在"));
        user.setOperator("system");
        user.setOperateIp("192.168.1.1");
        user.setOperateTime(new Date());

    }

    public void updateSysUser(SysUser sysUser) {
//        sysUserMapper.update(user);
    }


}
