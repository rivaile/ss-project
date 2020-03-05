package com.rainbow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.rainbow.common.RequestHolder;
import com.rainbow.dao.mapper.SysAclMapper;
import com.rainbow.dao.mapper.SysRoleUserMapper;
import com.rainbow.dao.mapper.SysUserMapper;
import com.rainbow.domain.SysAcl;
import com.rainbow.domain.SysAclExt;
import com.rainbow.domain.SysAclModuleExt;
import com.rainbow.domain.SysUser;
import com.rainbow.enums.ReturnCode;
import com.rainbow.service.BaseService;
import com.rainbow.service.ISysUserService;
import com.rainbow.vo.Response;
import com.rainbow.vo.SysUserReq;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserService extends BaseService<SysUserMapper, SysUser> implements ISysUserService {

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
    private SysAclModuleService sysAclModuleService;

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
    public boolean deleteUserById(Long userId) {
        SysUser sysUser = new SysUser();
//        sysUser.setId(userId);
        sysUser.setStatus(2);
        sysUser.setOperateTime(new Date());
        return updateById(sysUser);
    }

    @Override
    public void updateUser(SysUserReq userReq) {
        // todo: 电话邮箱校验
        SysUser user = getById(userReq.getId());
        BeanUtils.copyProperties(userReq,
                Preconditions.checkNotNull(user, "待更新的用户不存在"));
        user.setOperator("system");
        user.setOperateIp("192.168.1.1");
        user.setOperateTime(new Date());

        // todo: 操作日志 > 可以使用aop实现
        updateById(user);
    }

    @Override
    public IPage getSysUser(IPage page, SysUserReq req) {
        return page(page, new QueryWrapper<SysUser>().lambda()
                .eq(StringUtils.isNotEmpty(req.getUsername()), SysUser::getUsername, req.getUsername())
                .eq(StringUtils.isNotEmpty(req.getTelephone()), SysUser::getTelephone, req.getTelephone())
                .eq(req.getDeptId() != null, SysUser::getDeptId, req.getDeptId()));
    }

    @Override
    public List<SysAclModuleExt> userAclTree(int userId) {
        List<SysAcl> userAclList = getUserAclList(userId);

        List<SysAclExt> aclExts = userAclList.stream().map(it -> {
            SysAclExt acl = new SysAclExt();
            BeanUtils.copyProperties(it, acl);
            acl.setChecked(true);
            acl.setHasAcl(true);
            return acl;
        }).collect(Collectors.toList());

        return aclListToTree(aclExts);
    }

    private List<SysAclModuleExt> aclListToTree(List<SysAclExt> aclExts) {
        if (CollectionUtils.isEmpty(aclExts)) return Lists.newArrayList();
        List<SysAclModuleExt> aclModuleExtList = sysAclModuleService.aclModuleTree();


        Multimap<Integer, SysAclExt> moduleIdAclMap = ArrayListMultimap.create();
        for (SysAclExt acl : aclExts) {
            if (acl.getStatus() == 1) {
                moduleIdAclMap.put(acl.getAclModuleId(), acl);
            }
        }
        bindAclsWithOrder(aclModuleExtList, moduleIdAclMap);
        return aclModuleExtList;
    }

    private void bindAclsWithOrder(List<SysAclModuleExt> aclModuleExtList, Multimap<Integer, SysAclExt> moduleIdAclMap) {
        if (CollectionUtils.isEmpty(aclModuleExtList)) {
            return;
        }
        for (SysAclModuleExt dto : aclModuleExtList) {
            List<SysAclExt> aclDtoList = (List<SysAclExt>) moduleIdAclMap.get(dto.getId());
            if (CollectionUtils.isNotEmpty(aclDtoList)) {
                Collections.sort(aclDtoList, Comparator.comparingInt(SysAcl::getSeq)
                );
                dto.setAclList(aclDtoList);
            }
            bindAclsWithOrder(dto.getAclModuleList(), moduleIdAclMap);
        }
    }

    public List<SysAcl> getUserAclList(int userId) {
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
        SysUser sysUser = RequestHolder.getCurrentUser();
        if (sysUser.getMail().contains("admin")) {
            return true;
        }
        return false;
    }

}
