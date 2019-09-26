package com.rainbow.service.impl;

import com.google.common.base.Preconditions;
import com.rainbow.dao.mapper.SysAclMapper;
import com.rainbow.domain.SysAcl;
import com.rainbow.domain.SysRole;
import com.rainbow.enums.ReturnCode;
import com.rainbow.exception.BusinessException;
import com.rainbow.service.BaseService;
import com.rainbow.service.ISysAclService;
import com.rainbow.vo.SysAclReq;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-24 15:33
 */
@Service
public class SysAclService extends BaseService<SysAclMapper, SysAcl> implements ISysAclService {

    public void save(SysAclReq param) {
        // 是否存在相同名称的权限点

        SysAcl acl = new SysAcl();
        BeanUtils.copyProperties(param, acl);
        acl.setOperateIp("127.0.0.1");
        acl.setOperator("system");
        acl.setOperateTime(new Date());
        // todo: 操作日志 > 可以使用aop实现
        if (!save(acl)) throw new BusinessException(ReturnCode.INTERNAL_SERVER_ERROR);

    }

    public void update(SysAclReq param) {
        SysAcl acl = new SysAcl();
        BeanUtils.copyProperties(param,
                Preconditions.checkNotNull(getById(param.getId()), "待更新的角色不存在"));
        acl.setOperateIp("127.0.0.1");
        acl.setOperator("system");
        acl.setOperateTime(new Date());
        if (!updateById(acl)) throw new BusinessException("更新失败");
    }
}
