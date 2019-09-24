package com.rainbow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.rainbow.dao.mapper.SysRoleMapper;
import com.rainbow.dao.mapper.SysRoleUserMapper;
import com.rainbow.domain.SysRole;
import com.rainbow.domain.SysRoleUser;
import com.rainbow.enums.ReturnCode;
import com.rainbow.exception.BusinessException;
import com.rainbow.service.BaseService;
import com.rainbow.service.ISysRoleService;
import com.rainbow.vo.Response;
import com.rainbow.vo.SysRoleReq;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysRoleService extends BaseService<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    public void save(SysRoleReq param) {
        // 校验角色是否存在
        SysRole sysRole = new SysRole();
        BeanUtils.copyProperties(param, sysRole);
        sysRole.setOperateIp("127.0.0.1");
        sysRole.setOperator("system");
        sysRole.setOperateTime(new Date());
        // todo: 操作日志 > 可以使用aop实现
        if (!save(sysRole)) throw new BusinessException(ReturnCode.INTERNAL_SERVER_ERROR);
    }

    public void update(SysRoleReq param) {

        SysRole role = new SysRole();
        BeanUtils.copyProperties(param,
                Preconditions.checkNotNull(getById(param.getId()), "待更新的角色不存在"));
        role.setOperateIp("127.0.0.1");
        role.setOperator("system");
        role.setOperateTime(new Date());
        if (!updateById(role)) throw new BusinessException("更新失败");
    }

    public List<SysRole> listAll() {
        return list();
    }

    public List<SysRole> getRoleListByUserId(int userId) {

        List<Integer> roleIdList = roleUserMapper.selectList(new QueryWrapper<SysRoleUser>()
                .lambda()
                .select(SysRoleUser::getId)
                .eq(SysRoleUser::getRoleId, userId))
                .stream()
                .map(s -> s.getId())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return baseMapper.selectBatchIds(roleIdList);
    }

}

