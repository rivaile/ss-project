package com.rainbow.business.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.rainbow.business.system.dao.*;
import com.rainbow.common.BaseService;
import com.rainbow.business.system.service.ISystemRoleService;
import com.rainbow.common.RequestHolder;
import com.rainbow.domain.*;
import com.rainbow.enums.ReturnCode;
import com.rainbow.exception.BusinessException;

import com.rainbow.domain.vo.SystemRoleRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SystemRoleService extends BaseService<SystemRoleMapper, SystemRoleDO> implements ISystemRoleService {

    @Autowired
    private SystemRoleUserMapper systemRoleUserMapper;

    @Autowired
    private SystemRoleAuthMapper systemRoleAuthMapper;

    @Autowired
    private SystemAuthMapper systemAuthMapper;

    @Autowired
    private SystemAuthModuleMapper systemAuthModuleMapper;

    @Autowired
    private SystemAuthModuleService systemAuthoduleService;

    @Override
    public void addRole(SystemRoleRequest param) {

        int count = count(new QueryWrapper<SystemRoleDO>().lambda()
                .eq(SystemRoleDO::getName, param.getName()));
        if (count > 0) {
            throw new BusinessException(ReturnCode.ROLE_NAME_EXSIT_ERROR);
        }

        // 校验角色是否存在
        SystemRoleDO role = new SystemRoleDO();
        BeanUtils.copyProperties(param, role);
        role.setOperateIp("127.0.0.1");
        role.setOperator("system");
        role.setOperateTime(new Date());

        save(role);
    }

    @Override
    public void updateRole(Integer id, SystemRoleRequest param) {

        Preconditions.checkNotNull(getById(id), "待更新的角色不存在!");

        int count = count(new QueryWrapper<SystemRoleDO>().lambda()
                .eq(SystemRoleDO::getName, param.getName())
                .ne(SystemRoleDO::getId, id)
        );
        if (count > 0) {
            throw new BusinessException(ReturnCode.ROLE_NAME_EXSIT_ERROR);
        }

        SystemRoleDO role = new SystemRoleDO();
        BeanUtils.copyProperties(param, role);

        role.setId(id);
        role.setOperateIp("127.0.0.1");
        role.setOperator("system");
        role.setOperateTime(new Date());
        updateById(role);
    }



    @Override
    public IPage<SystemRoleDO> pageList(PageRequest request) {
        Page page = new Page();
        page.setCurrent(request.getCurrent());
        page.setSize(request.getPageSize());
        page.setOrders(request.getOrders());
        IPage<SystemRoleDO> pageResult = page(page, new QueryWrapper<SystemRoleDO>().lambda());
        return pageResult;
    }



    public List<SystemRoleDO> getRoleListByUserId(int userId) {

        List<Integer> roleIdList = systemRoleUserMapper.selectList(new QueryWrapper<SystemRoleUserDO>()
                .lambda()
                .select(SystemRoleUserDO::getId)
                .eq(SystemRoleUserDO::getRoleId, userId))
                .stream()
                .map(s -> s.getId())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        return baseMapper.selectBatchIds(roleIdList);
    }

    public List<SystemAuthModuleBO> roleTreeByRoleId(int roleId) {

        // 1. 获取用户已分配的权限点
        List<SystemAuthDO> userAuthList = getCurrentUserAclList();
        // 2. 当前角色分配的权限点
//        List<SystemAuthDO> roleAuthList = getRoleAuthList(roleId);
        List<Integer> authIdList = systemRoleAuthMapper.selectList(new QueryWrapper<SystemRoleAuthDO>().lambda()
                .select(SystemRoleAuthDO::getAuthId)
                .eq(SystemRoleAuthDO::getRoleId, roleId))
                .stream()
                .map(it -> it.getAuthId())
                .distinct()
                .collect(Collectors.toList());
        ;

        // 3. 当前系统的权限点.
        List<SystemAuthExt> authExtList = Lists.newArrayList();

        systemAuthMapper.selectList(new QueryWrapper<>()).forEach(it -> {
            SystemAuthExt auth = new SystemAuthExt();
            BeanUtils.copyProperties(it, auth);
            if (userAuthList.contains(it.getId())) {
                auth.setHasAuth(true);
            }
            if (authIdList.contains(it.getId())) {
                auth.setChecked(true);
            }
            authExtList.add(auth);
        });

        /*----------------------挂载权限到模块树上-------------------*/

        if (CollectionUtils.isEmpty(authExtList)) {
            return Lists.newArrayList();
        }
        // 权限模块树...
        List<SystemAuthModuleBO> authModuleTree = systemAuthoduleService.getAuthModuleTree();

        Map<Integer, List<SystemAuthExt>> moduleIdMap = authExtList.stream()
                .filter(it -> it.getStatus() == 1)
                .collect(Collectors.groupingBy(SystemAuthExt::getAuthModuleId));

        bindAuthsOnModuleTree(authModuleTree, moduleIdMap);

        return authModuleTree;
    }

    void bindAuthsOnModuleTree(List<SystemAuthModuleBO> authModuleTree,
                               Map<Integer, List<SystemAuthExt>> moduleIdMap) {
        if (CollectionUtils.isEmpty(authModuleTree)) {
            return;
        }

        authModuleTree.forEach(it -> {
            List<SystemAuthExt> authList = moduleIdMap.get(it.getId());
            if (CollectionUtils.isNotEmpty(authList)) {
                Collections.sort(authList, Comparator.comparingInt(SystemAuthDO::getSeq));
                it.setAuthList(authList);
            }
            bindAuthsOnModuleTree(it.getModuleList(), moduleIdMap);
        });
    }

    public List<SystemAuthDO> getRoleAuthList(int roleId) {
        List<Integer> authIdList = systemRoleAuthMapper.selectList(new QueryWrapper<SystemRoleAuthDO>().lambda()
                .select(SystemRoleAuthDO::getAuthId)
                .eq(SystemRoleAuthDO::getRoleId, roleId))
                .stream()
                .map(it -> it.getAuthId())
                .distinct()
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(authIdList)) {
            return Lists.newArrayList();
        }
        return systemAuthMapper.selectList(new QueryWrapper<SystemAuthDO>().lambda()
                .in(SystemAuthDO::getId, authIdList));
    }


    private List<SystemAuthDO> getCurrentUserAclList() {

//        long userId = RequestHolder.getCurrentUser().getId();
        long userId = 1;

//        if (isSuperAdmin()) {
//            return systemAuthMapper.selectList(null);
//        }

        List<Integer> userRoleIdList = systemRoleUserMapper.selectList(new QueryWrapper<SystemRoleUserDO>()
                .lambda()
                .select(SystemRoleUserDO::getRoleId)
                .eq(SystemRoleUserDO::getId, userId))
                .stream().map(it -> it.getRoleId())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }

        List<Integer> userAuthIdList = systemRoleAuthMapper.selectList(new QueryWrapper<SystemRoleAuthDO>()
                .lambda()
                .select(SystemRoleAuthDO::getAuthId)
                .in(SystemRoleAuthDO::getRoleId, userRoleIdList))
                .stream()
                .map(it -> it.getAuthId())
                .distinct()
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(userAuthIdList)) {
            return Lists.newArrayList();
        }

        return systemAuthMapper.selectList(new QueryWrapper<SystemAuthDO>().lambda()
                .in(SystemAuthDO::getId, userAuthIdList));
    }

    public boolean isSuperAdmin() {
        // 这里是我自己定义了一个假的超级管理员规则，实际中要根据项目进行修改
        // 可以是配置文件获取，可以指定某个用户，也可以指定某个角色
        SystemUserDO sysUser = RequestHolder.getCurrentUser();
        if (sysUser.getMail().contains("admin")) {
            return true;
        }
        return false;
    }


}

