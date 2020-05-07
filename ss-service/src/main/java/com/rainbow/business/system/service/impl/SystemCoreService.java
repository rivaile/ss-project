package com.rainbow.business.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.rainbow.business.system.dao.SystemAuthMapper;
import com.rainbow.business.system.dao.SystemRoleAuthMapper;
import com.rainbow.business.system.dao.SystemRoleUserMapper;
import com.rainbow.common.RequestHolder;
import com.rainbow.domain.SystemAuthDO;
import com.rainbow.domain.SystemRoleAuthDO;
import com.rainbow.domain.SystemRoleUserDO;
import com.rainbow.domain.SystemUserDO;
import com.rainbow.util.JsonMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2020-05-06 17:39
 */
@Service
public class SystemCoreService {

    @Autowired
    private SystemAuthMapper systemAuthMapper;

    @Autowired
    private SystemRoleUserMapper systemRoleUserMapper;

    @Autowired
    private SystemRoleAuthMapper systemRoleAuthMapper;


    public boolean hasUrlAuth(String servletPath) {
        if (isSuperAdmin()) {
            return true;
        }

        List<SystemAuthDO> authList = systemAuthMapper.selectList(new QueryWrapper<SystemAuthDO>().lambda()
                .eq(SystemAuthDO::getUrl, servletPath));
        if (CollectionUtils.isEmpty(authList)) {
            return true;
        }
        Set<Integer> userAuthIdSet = getCurrentUserAuthList().stream()
                .map(it -> it.getId())
                .collect(Collectors.toSet());

        boolean hasValidAuth = false;
        // 规则：只要有一个权限点有权限，那么我们就认为有访问权限
        for (SystemAuthDO auth : authList) {
            // 判断一个用户是否具有某个权限点的访问权限
            if (auth == null || auth.getStatus() != 1) { // 权限点无效
                continue;
            }
            hasValidAuth = true;
            if (userAuthIdSet.contains(auth.getId())) {
                return true;
            }
        }

        if (!hasValidAuth) {
            return true;
        }
        return false;
    }

    public boolean isSuperAdmin() {
        SystemUserDO sysUser = RequestHolder.getCurrentUser();
        if (sysUser.getMail().contains("admin")) {
            return true;
        }
        return false;
    }

    public List<SystemAuthDO> getCurrentUserAuthList() {
        Long userId = RequestHolder.getCurrentUser().getId();
//        String cacheValue = sysCacheService.getFromCache(CacheKeyConstants.USER_ACLS, String.valueOf(userId));
        String cacheValue = null;
        if (StringUtils.isBlank(cacheValue)) {
            List<SystemAuthDO> aclList = getUserAuthList(userId);
            if (CollectionUtils.isNotEmpty(aclList)) {
//                sysCacheService.saveCache(JsonMapper.obj2String(aclList), 600, CacheKeyConstants.USER_ACLS, String.valueOf(userId));
            }
            return aclList;
        }
        return JsonMapper.string2Obj(cacheValue, new TypeReference<List<SystemAuthDO>>() {
        });
    }

    public List<SystemAuthDO> getUserAuthList(Long userId) {

        if (isSuperAdmin()) {
            return systemAuthMapper.selectList(null);
        }

        List<Integer> roleIdList = systemRoleUserMapper.selectList(new QueryWrapper<SystemRoleUserDO>().lambda()
                .eq(SystemRoleUserDO::getUserId, userId))
                .stream()
                .map(it -> it.getRoleId())
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }

        List<Integer> authIdList = systemRoleAuthMapper.selectList(new QueryWrapper<SystemRoleAuthDO>().lambda()
                .select(SystemRoleAuthDO::getAuthId)
                .in(SystemRoleAuthDO::getRoleId, roleIdList))
                .stream()
                .map(it -> it.getAuthId())
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(authIdList)) {
            return Lists.newArrayList();
        }

        List<SystemAuthDO> authList = systemAuthMapper.selectList(new QueryWrapper<SystemAuthDO>().lambda()
                .in(SystemAuthDO::getId, authIdList));
        return authList;
    }

}
