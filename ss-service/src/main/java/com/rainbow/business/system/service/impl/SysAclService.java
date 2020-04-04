package com.rainbow.business.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.rainbow.business.system.dao.SysAclMapper;
import com.rainbow.business.system.service.BaseService;
import com.rainbow.business.system.service.ISysAclService;
import com.rainbow.domain.SysAcl;
import com.rainbow.enums.ReturnCode;
import com.rainbow.exception.BusinessException;
import com.rainbow.vo.AclParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-24 15:33
 */
@Service
public class SysAclService extends BaseService<SysAclMapper, SysAcl> implements ISysAclService {

    public void saveAcl(AclParam param) {
        // 是否存在相同名称的权限点
        if (count(new QueryWrapper<SysAcl>().lambda()
                .eq(SysAcl::getName, param.getName())
                .eq(SysAcl::getAclModuleId, param.getAclModuleId())) > 0) {
            throw new BusinessException("当前权限模块下面存在相同名称的权限点!");
        }

        SysAcl acl = new SysAcl();
        BeanUtils.copyProperties(param, acl);
        acl.setCode(generateCode());
        acl.setOperateIp("127.0.0.1");
        acl.setOperator("system");
        acl.setOperateTime(new Date());
        // todo: 操作日志 > 可以使用aop实现
        if (!save(acl)) throw new BusinessException(ReturnCode.INTERNAL_SERVER_ERROR);
    }

    public void updateAcl(AclParam param) {

        BeanUtils.copyProperties(param,
                Preconditions.checkNotNull(getById(param.getId()), "待更新的角色不存在"));

        if (count(new QueryWrapper<SysAcl>().lambda()
                .eq(SysAcl::getName, param.getName())
                .eq(SysAcl::getAclModuleId, param.getAclModuleId())
                .notIn(SysAcl::getId, param.getId())) > 0) {
            throw new BusinessException("当前权限模块下面存在相同名称的权限点!");
        }

        SysAcl acl = new SysAcl();
        BeanUtils.copyProperties(param, acl);
        acl.setOperateIp("127.0.0.1");
        acl.setOperator("system");
        acl.setOperateTime(new Date());
        if (!updateById(acl)) throw new BusinessException("更新失败");
    }

    public IPage<SysAcl> getAclListByModuleId(Integer aclModuleId, Page page) {
        return page(page, new QueryWrapper<SysAcl>().lambda().eq(SysAcl::getAclModuleId, aclModuleId));
    }


    public String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int) (Math.random() * 100);
    }

}
