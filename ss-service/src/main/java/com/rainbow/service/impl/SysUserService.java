package com.rainbow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Preconditions;
import com.rainbow.dao.mapper.SysUserMapper;
import com.rainbow.domain.SysUser;
import com.rainbow.enums.ReturnCode;
import com.rainbow.service.BaseService;
import com.rainbow.service.ISysUserService;
import com.rainbow.vo.Response;
import com.rainbow.vo.SysUserReq;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysUserService extends BaseService<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Response<String> addSysUser(SysUserReq userReq) {
        //todo: 电话邮箱校验

        // 判断该用户是否存在

        SysUser user = new SysUser();
        BeanUtils.copyProperties(userReq, user);
//        user.setPassword(passwordEncoder.encode("123456"));
        user.setPassword("123456");
        user.setOperator("system");
        user.setOperateIp("192.168.1.1");
        user.setOperateTime(new Date());

        // todo: 操作日志 > 可以使用aop实现
        if (save(user))
            return Response.success("注册成功");
        else
            return Response.error(ReturnCode.AUTH_SERVER_1010006, "注册失败");

    }

    @Override
    public void deleteByUser() {

    }

    @Override
    public Response updateUser(SysUserReq userReq) {
        // todo: 电话邮箱校验
        SysUser user = getById(userReq.getId());
        BeanUtils.copyProperties(userReq,
                Preconditions.checkNotNull(user, "待更新的用户不存在"));
        user.setOperator("system");
        user.setOperateIp("192.168.1.1");
        user.setOperateTime(new Date());

        // todo: 操作日志 > 可以使用aop实现
        if (updateById(user))
            return Response.success("修改成功");
        else
            return Response.error(ReturnCode.AUTH_SERVER_1010006, "修改失败");
    }

    @Override
    public IPage getSysUser(IPage page, SysUserReq req) {
        return page(page, new QueryWrapper<SysUser>().lambda()
                .eq(StringUtils.isNotEmpty(req.getUsername()), SysUser::getUsername, req.getUsername())
                .eq(StringUtils.isNotEmpty(req.getTelephone()), SysUser::getTelephone, req.getTelephone())
                .eq(req.getDeptId() != null, SysUser::getDeptId, req.getDeptId()));
    }

}
