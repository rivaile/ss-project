package com.rainbow.business.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.rainbow.business.system.dao.SysRoleMapper;
import com.rainbow.business.system.dao.SysRoleUserMapper;
import com.rainbow.business.system.dao.SystemUserMapper;
import com.rainbow.domain.SystemRole;
import com.rainbow.domain.SysRoleUser;
import com.rainbow.domain.SystemUser;
import com.rainbow.business.system.service.BaseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-10-12 17:32
 */
@Service
public class SysRoleUserService extends BaseService<SysRoleUserMapper, SysRoleUser> {

    @Autowired
    private SystemUserMapper userMapper;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    public void changeRoleUsers(int roleId, List<Integer> userIdList) {

        List<Integer> originUserIdList = list(new QueryWrapper<SysRoleUser>().lambda()
                .select(SysRoleUser::getUserId)
                .eq(SysRoleUser::getRoleId, roleId))
                .stream()
                .map(it -> it.getUserId())
                .collect(Collectors.toList());

        if (originUserIdList.size() == userIdList.size()) {
            Set<Integer> originUserIdSet = Sets.newHashSet(originUserIdList);
            Set<Integer> userIdSet = Sets.newHashSet(userIdList);
            originUserIdSet.removeAll(userIdSet);
            if (CollectionUtils.isEmpty(originUserIdSet)) return;
        }

        updateRoleUsers(roleId, userIdList);
    }

    private void updateRoleUsers(int roleId, List<Integer> userIdList) {

        remove(new QueryWrapper<SysRoleUser>().lambda().eq(SysRoleUser::getRoleId, roleId));

        if (CollectionUtils.isNotEmpty(userIdList)) {

            List<SysRoleUser> roleAclList = userIdList.stream().map(it -> {

                SysRoleUser roleUSer = new SysRoleUser();
                roleUSer.setRoleId(roleId);
                roleUSer.setUserId(it);
                roleUSer.setOperateIp("127.0.0.1");
                roleUSer.setOperateTime(new Date());
                roleUSer.setOperator("sys");
                return roleUSer;

            }).collect(Collectors.toList());

            saveBatch(roleAclList);
        }
    }

    public List<SystemUser> getUserListByRoleId(int roleId) {

        List<Integer> userIdList = list(new QueryWrapper<SysRoleUser>().lambda()
                .select(SysRoleUser::getUserId)
                .eq(SysRoleUser::getRoleId, roleId))
                .stream()
                .map(it -> it.getUserId())
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayList();
        }

        return userMapper.selectBatchIds(userIdList);
    }

    public List<Integer> getRoleIdListByUserId(int userId) {
        return list(new QueryWrapper<SysRoleUser>().lambda()
                .select(SysRoleUser::getRoleId).eq(SysRoleUser::getUserId, userId))
                .stream()
                .map(it -> it.getRoleId()).collect(Collectors.toList());
    }

    public Collection<SystemRole> getRoleListByUserId(int userId) {
        List<Integer> roleIdList = getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return sysRoleMapper.selectBatchIds(roleIdList);
    }
}
