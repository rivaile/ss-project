package com.rainbow.business.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Sets;
import com.rainbow.business.system.dao.SystemRoleAuthMapper;
import com.rainbow.business.system.service.ISystemRoleAuthService;
import com.rainbow.domain.SystemRoleAuthDO;
import com.rainbow.common.BaseService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-10-12 17:27
 */
@Service
public class SystemRoleAuthService extends BaseService<SystemRoleAuthMapper, SystemRoleAuthDO> implements ISystemRoleAuthService {


    @Override
    public void changeRoleAuths(int roleId, List<Integer> authIdList) {

        List<Integer> originAuthIdList = list(new QueryWrapper<SystemRoleAuthDO>().lambda()
                .select(SystemRoleAuthDO::getAuthId)
                .eq(SystemRoleAuthDO::getRoleId, roleId))
                .stream()
                .map(it -> it.getAuthId())
                .collect(Collectors.toList());

        if (originAuthIdList.size() == authIdList.size()) {
            Set<Integer> originAuthIdSet = Sets.newHashSet(originAuthIdList);
            Set<Integer> authIdSet = Sets.newHashSet(authIdList);
            originAuthIdSet.removeAll(authIdSet);
            if (CollectionUtils.isEmpty(originAuthIdSet)) {
                return;
            }
        }

        updateRoleAuths(roleId, authIdList);
    }

    @Transactional
    void updateRoleAuths(int roleId, List<Integer> authIdList) {

        remove(new QueryWrapper<SystemRoleAuthDO>().lambda().eq(SystemRoleAuthDO::getRoleId, roleId));

        if (CollectionUtils.isNotEmpty(authIdList)) {

            List<SystemRoleAuthDO> roleAclList = authIdList.stream().map(it -> {
                SystemRoleAuthDO roleAuth = new SystemRoleAuthDO();
                roleAuth.setRoleId(roleId);
                roleAuth.setAuthId(it);
                roleAuth.setOperateIp("127.0.0.1");
                roleAuth.setOperateTime(new Date());
                roleAuth.setOperator("sys");
                return roleAuth;
            }).collect(Collectors.toList());

            saveBatch(roleAclList);
        }

    }

    public List<Integer> getAuthIdListByRoleIdList(List<Integer> roleIds) {
        return list(new QueryWrapper<SystemRoleAuthDO>().lambda()
                .select(SystemRoleAuthDO::getAuthId)
                .in(SystemRoleAuthDO::getRoleId, roleIds)).stream()
                .map(it -> it.getAuthId()).collect(Collectors.toList());
    }
}
