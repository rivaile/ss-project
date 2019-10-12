package com.rainbow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Sets;
import com.rainbow.dao.mapper.SysRoleAclMapper;
import com.rainbow.domain.SysRoleAcl;
import com.rainbow.service.BaseService;
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
public class SysRoleAclService extends BaseService<SysRoleAclMapper, SysRoleAcl> {


    public void changeRoleAcls(int roleId, List<Integer> aclIdList) {

        List<Integer> originAclIdList = list(new QueryWrapper<SysRoleAcl>().lambda()
                .select(SysRoleAcl::getAclId)
                .eq(SysRoleAcl::getRoleId, roleId))
                .stream()
                .map(it -> it.getAclId())
                .collect(Collectors.toList());

        if (originAclIdList.size() == aclIdList.size()) {
            Set<Integer> originAclIdSet = Sets.newHashSet(originAclIdList);
            Set<Integer> aclIdSet = Sets.newHashSet(aclIdList);
            originAclIdSet.removeAll(aclIdSet);
            if (CollectionUtils.isEmpty(originAclIdSet)) {
                return;
            }
        }

        updateRoleAcls(roleId, aclIdList);
    }

    @Transactional
    void updateRoleAcls(int roleId, List<Integer> aclIdList) {
        remove(new QueryWrapper<SysRoleAcl>().lambda().eq(SysRoleAcl::getRoleId, roleId));

        if (CollectionUtils.isNotEmpty(aclIdList)) {

            List<SysRoleAcl> roleAclList = aclIdList.stream().map(it -> {
                SysRoleAcl roleAcl = new SysRoleAcl();
                roleAcl.setRoleId(roleId);
                roleAcl.setAclId(it);
                roleAcl.setOperateIp("127.0.0.1");
                roleAcl.setOperateTime(new Date());
                roleAcl.setOperator("sys");
                return roleAcl;
            }).collect(Collectors.toList());

            saveBatch(roleAclList);
        }

    }

}
