package com.rainbow.business.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.rainbow.business.system.dao.SystemAuthMapper;
import com.rainbow.common.BaseService;
import com.rainbow.business.system.service.ISystemAuthService;
import com.rainbow.domain.PageRequest;
import com.rainbow.domain.SystemAuthDO;
import com.rainbow.exception.BusinessException;
import com.rainbow.domain.vo.AuthRequest;
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
public class SystemAuthService extends BaseService<SystemAuthMapper, SystemAuthDO> implements ISystemAuthService {

    @Override
    public void saveAuth(AuthRequest param) {
        // 是否存在相同名称的权限点
        int count = count(new QueryWrapper<SystemAuthDO>().lambda()
                .eq(SystemAuthDO::getName, param.getName())
                .eq(SystemAuthDO::getAuthModuleId, param.getAuthModuleId()));
        if (count > 0) {
            throw new BusinessException("当前权限模块下面存在相同名称的权限点!");

        }
        SystemAuthDO auth = new SystemAuthDO();
        BeanUtils.copyProperties(param, auth);
        auth.setCode(generateCode());
        auth.setOperateIp("127.0.0.1");
        auth.setOperator("system");
        auth.setOperateTime(new Date());

        save(auth);
    }

    @Override
    public void updateAuth(Integer id, AuthRequest param) {

        BeanUtils.copyProperties(param, Preconditions.checkNotNull(getById(id), "待更新的角色不存在"));

        int count = count(new QueryWrapper<SystemAuthDO>().lambda()
                .eq(SystemAuthDO::getName, param.getName())
                .eq(SystemAuthDO::getAuthModuleId, param.getAuthModuleId())
                .notIn(SystemAuthDO::getId, id));

        if (count > 0) {
            throw new BusinessException("当前权限模块下面存在相同名称的权限点!");
        }

        SystemAuthDO auth = new SystemAuthDO();
        BeanUtils.copyProperties(param, auth);

        auth.setId(id);
        auth.setOperateIp("127.0.0.1");
        auth.setOperator("system");
        auth.setOperateTime(new Date());

        updateById(auth);
    }

    @Override
    public IPage<SystemAuthDO> pageAuthListByModuleId(Integer authModuleId, PageRequest pageRequest) {

        Page page = new Page();
        page.setCurrent(pageRequest.getCurrent());
        page.setSize(pageRequest.getPageSize());
        page.setOrders(pageRequest.getOrders());

        IPage result = page(page, new QueryWrapper<SystemAuthDO>().lambda()
                .eq(SystemAuthDO::getAuthModuleId, authModuleId));

        return result;
    }

    public String generateCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date()) + "_" + (int) (Math.random() * 100);
    }
}
