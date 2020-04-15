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

import com.rainbow.vo.SystemRoleRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SystemRoleService extends BaseService<SystemRoleMapper, SystemRoleDO> implements ISystemRoleService {

    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;

    @Autowired
    private SysRoleAclMapper sysRoleAclMapper;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysAclModuleMapper sysAclModuleMapper;

    @Autowired
    private SystemAuthModuleService sysAclModuleService;


    public void save(SystemRoleRequest param) {
        // 校验角色是否存在
        SystemRoleDO sysRole = new SystemRoleDO();
        BeanUtils.copyProperties(param, sysRole);
        sysRole.setOperateIp("127.0.0.1");
        sysRole.setOperator("system");
        sysRole.setOperateTime(new Date());
        // todo: 操作日志 > 可以使用aop实现
        if (!save(sysRole)) throw new BusinessException(ReturnCode.INTERNAL_SERVER_ERROR);
    }

    public void update(SystemRoleRequest param) {

        SystemRoleDO role = new SystemRoleDO();
        BeanUtils.copyProperties(param,
                Preconditions.checkNotNull(getById(param.getId()), "待更新的角色不存在"));
        role.setOperateIp("127.0.0.1");
        role.setOperator("system");
        role.setOperateTime(new Date());
        if (!updateById(role)) throw new BusinessException("更新失败");
    }

    public List<SystemRoleDO> listAll() {

        return list();
    }

    public List<SystemRoleDO> getRoleListByUserId(int userId) {

        List<Integer> roleIdList = sysRoleUserMapper.selectList(new QueryWrapper<SysRoleUser>()
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

    public List<SystemAuthModuleBO> roleTreeByRoleId(int roleId) {

        // todo  1. 获取用户已分配的权限点   意义 ???
        List<SystemAuthDO> userAclList = getCurrentUserAclList();
        // 2. 当前角色分配的权限点
        List<SystemAuthDO> roleAclList = getRoleAclList(roleId);

        // 3. 当前系统的权限点.
        List<SysAclExt> aclExtList = Lists.newArrayList();

        sysAclMapper.selectList(null).forEach(it -> {
            SysAclExt sysAclExt = new SysAclExt();
            BeanUtils.copyProperties(it, sysAclExt);
            if (userAclList.contains(it.getId())) {
                sysAclExt.setHasAcl(true);
            }
            if (roleAclList.contains(it.getId())) {
                sysAclExt.setChecked(true);
            }
            aclExtList.add(sysAclExt);
        });

        /*----------------------挂载权限到模块树上-------------------*/

        if (CollectionUtils.isEmpty(aclExtList)) {
            return Lists.newArrayList();
        }
        // 权限模块树...
        List<SystemAuthModuleBO> aclModuleTree = sysAclModuleService.getAuthModuleTree();

        Map<Integer, List<SysAclExt>> moduleIdAclMap = aclExtList.stream()
                .filter(it -> it.getStatus() == 1)
                .collect(Collectors.groupingBy(SysAclExt::getAuthModuleId));

        bindAclsOnModuleTree(aclModuleTree, moduleIdAclMap);





        return aclModuleTree;
    }


    @Transactional
    private void bindAclsOnModuleTree(List<SystemAuthModuleBO> aclModuleTree,
                                      Map<Integer, List<SysAclExt>> moduleIdAclMap) {
        aclModuleTree.forEach(it -> {
            List<SysAclExt> aclList = moduleIdAclMap.get(it.getId());
            if (CollectionUtils.isNotEmpty(aclList)) {
                Collections.sort(aclList, Comparator.comparingInt(SystemAuthDO::getSeq));
                it.setAuthList(aclList);
            }
        });
    }

    public List<SystemAuthDO> getRoleAclList(int roleId) {
        List<Integer> aclIdList = sysRoleAclMapper.selectList(new QueryWrapper<SysRoleAcl>().lambda()
                .select(SysRoleAcl::getAclId)
                .eq(SysRoleAcl::getRoleId, roleId))
                .stream()
                .map(it -> it.getAclId())
                .distinct()
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        return sysAclMapper.selectList(new QueryWrapper<SystemAuthDO>().lambda()
                .in(SystemAuthDO::getId, aclIdList));
    }


    private List<SystemAuthDO> getCurrentUserAclList() {

//        long userId = RequestHolder.getCurrentUser().getId();
        long userId = 1;

//        if (isSuperAdmin()) {
//            return sysAclMapper.selectList(null);
//        }

        List<Integer> userRoleIdList = sysRoleUserMapper.selectList(new QueryWrapper<SysRoleUser>()
                .lambda()
                .select(SysRoleUser::getRoleId)
                .eq(SysRoleUser::getId, userId))
                .stream().map(it -> it.getRoleId())
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userRoleIdList)) {
            return Lists.newArrayList();
        }

        List<Integer> userAclIdList = sysRoleAclMapper.selectList(new QueryWrapper<SysRoleAcl>()
                .lambda()
                .select(SysRoleAcl::getAclId)
                .in(SysRoleAcl::getRoleId, userRoleIdList))
                .stream()
                .map(it -> it.getAclId())
                .distinct()
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(userAclIdList)) {
            return Lists.newArrayList();
        }

        return sysAclMapper.selectList(new QueryWrapper<SystemAuthDO>().lambda()
                .in(SystemAuthDO::getId, userAclIdList));
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


    @Override
    public IPage<SystemRoleDO> pageList(PageRequest request) {

        Page page = new Page();
        page.setCurrent(request.getCurrent());
        page.setSize(request.getPageSize());
        page.setOrders(request.getOrders());
        IPage<SystemRoleDO> pageResult = page(page, new QueryWrapper<SystemRoleDO>().lambda());
        return pageResult;
    }
}

