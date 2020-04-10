package com.rainbow.business.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.rainbow.common.RequestHolder;
import com.rainbow.business.system.dao.SysAclMapper;
import com.rainbow.business.system.dao.SysRoleUserMapper;
import com.rainbow.business.system.dao.SystemUserMapper;
import com.rainbow.domain.SystemAuthDO;
import com.rainbow.domain.SysAclExt;
import com.rainbow.domain.SystemAuthModuleBO;
import com.rainbow.domain.SystemUserDO;
import com.rainbow.common.BaseService;
import com.rainbow.business.system.service.ISystemUserService;
import com.rainbow.enums.ReturnCode;
import com.rainbow.enums.SystemUserStatus;
import com.rainbow.exception.BusinessException;
import com.rainbow.util.IpUtil;
import com.rainbow.vo.req.PageSystemUserRequest;
import com.rainbow.vo.req.SystemUserRequest;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemUserService extends BaseService<SystemUserMapper, SystemUserDO> implements ISystemUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SysAclMapper sysAclMapper;

    @Autowired
    private SysRoleUserMapper roleUserMapper;

    @Autowired
    private SysRoleUserService roleUserService;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    @Autowired
    private SystemAuthModuleService sysAclModuleService;

    @Override
    public boolean addUser(@Valid SystemUserRequest request) {

        int usernameExistCount = count(new QueryWrapper<SystemUserDO>().lambda()
                .eq(SystemUserDO::getUsername, request.getUsername()));
        if (usernameExistCount > 0) {
            throw new BusinessException(ReturnCode.USERNAME_EXSIT_ERROR);
        }

        int telephoneExistCount = count(new QueryWrapper<SystemUserDO>().lambda()
                .eq(SystemUserDO::getTelephone, request.getTelephone()));
        if (telephoneExistCount > 0) {
            throw new BusinessException(ReturnCode.TELEPHONE_EXSIT_ERROR);
        }

        int mailExistCount = count(new QueryWrapper<SystemUserDO>().lambda()
                .eq(SystemUserDO::getMail, request.getMail()));
        if (mailExistCount > 0) {
            throw new BusinessException(ReturnCode.MAIL_EXSIT_ERROR);
        }

        SystemUserDO user = new SystemUserDO();
        BeanUtils.copyProperties(request, user);
//        user.setPassword(passwordEncoder.encode("123456"));
        user.setPassword("123456");
        user.setOperator("system");
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());

        // todo: 操作日志 > 可以使用aop实现
        return save(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        SystemUserDO sysUser = new SystemUserDO();
        sysUser.setId(id);
        sysUser.setStatus(SystemUserStatus.DELETE.getValue());
        sysUser.setOperateTime(new Date());
        return updateById(sysUser);
    }

    @Override
    public boolean updateUser(Long id, SystemUserRequest request) {
        SystemUserDO user = getById(id);
        BeanUtils.copyProperties(request, Preconditions.checkNotNull(user, "待更新的用户不存在"));

        int usernameExistCount = count(new QueryWrapper<SystemUserDO>().lambda()
                .eq(SystemUserDO::getUsername, request.getUsername())
                .ne(SystemUserDO::getId, id));
        if (usernameExistCount > 0) {
            throw new BusinessException(ReturnCode.USERNAME_EXSIT_ERROR);
        }

        int telephoneExistCount = count(new QueryWrapper<SystemUserDO>().lambda()
                .eq(SystemUserDO::getTelephone, request.getTelephone())
                .ne(SystemUserDO::getId, id));
        if (telephoneExistCount > 0) {
            throw new BusinessException(ReturnCode.TELEPHONE_EXSIT_ERROR);
        }

        int mailExistCount = count(new QueryWrapper<SystemUserDO>().lambda()
                .eq(SystemUserDO::getMail, request.getMail())
                .ne(SystemUserDO::getId, id));
        if (mailExistCount > 0) {
            throw new BusinessException(ReturnCode.MAIL_EXSIT_ERROR);
        }

        user.setId(id);
        user.setOperator("system");
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());
        return updateById(user);
    }

    @Override
    public IPage<SystemUserDO> pageList(PageSystemUserRequest request) {

        Page page = new Page();
        page.setCurrent(request.getCurrent());
        page.setSize(request.getPageSize());
        page.setOrders(request.getOrders());

        IPage<SystemUserDO> pageResult = page(page, new QueryWrapper<SystemUserDO>().lambda()
                .eq(StringUtils.isNotEmpty(request.getUsername()), SystemUserDO::getUsername, request.getUsername())
                .eq(StringUtils.isNotEmpty(request.getTelephone()), SystemUserDO::getTelephone, request.getTelephone())
                .eq(request.getDeptId() != null, SystemUserDO::getDeptId, request.getDeptId()));

        return pageResult;
    }

    @Override
    public List<SystemAuthModuleBO> userAclTree(int userId) {
        List<SystemAuthDO> userAclList = getUserAclList(userId);

        List<SysAclExt> aclExts = userAclList.stream().map(it -> {
            SysAclExt acl = new SysAclExt();
            BeanUtils.copyProperties(it, acl);
            acl.setChecked(true);
            acl.setHasAcl(true);
            return acl;
        }).collect(Collectors.toList());

        return aclListToTree(aclExts);
    }

    private List<SystemAuthModuleBO> aclListToTree(List<SysAclExt> aclExts) {
        if (CollectionUtils.isEmpty(aclExts)) return Lists.newArrayList();
        List<SystemAuthModuleBO> aclModuleExtList = sysAclModuleService.getAuthModuleTree();


        Multimap<Integer, SysAclExt> moduleIdAclMap = ArrayListMultimap.create();
        for (SysAclExt acl : aclExts) {
            if (acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclModuleId(), acl);
            }
        }
        bindAclsWithOrder(aclModuleExtList, moduleIdAclMap);
        return aclModuleExtList;
    }

    private void bindAclsWithOrder(List<SystemAuthModuleBO> aclModuleExtList, Multimap<Integer, SysAclExt> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleExtList)) {
            return;
        }
        for (SystemAuthModuleBO dto : aclModuleExtList) {
            List<SysAclExt> aclDtoList = (List<SysAclExt>) moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclDtoList)) {
                Collections.sort(aclDtoList, Comparator.comparingInt(SystemAuthDO::getSeq)
                );
                dto.setAuthList(aclDtoList);
            }
            bindAclsWithOrder(dto.getModuleList(), moduleIdAclMap);
        }
    }

    public List<SystemAuthDO> getUserAclList(int userId) {
        if (isSuperAdmin()) {
            return sysAclMapper.selectList(null);
        }

        List<Integer> roleIds = roleUserService.getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) return Lists.newArrayList();

        List<Integer> userAclIdList = sysRoleAclService.getAclIdListByRoleIdList(roleIds);
        if (CollectionUtils.isEmpty(userAclIdList)) return Lists.newArrayList();
        return sysAclMapper.selectBatchIds(userAclIdList);
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
