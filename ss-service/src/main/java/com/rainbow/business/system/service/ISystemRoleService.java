package com.rainbow.business.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbow.domain.PageRequest;
import com.rainbow.domain.SystemRoleDO;
import com.rainbow.domain.vo.SystemRoleRequest;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-24 15:33
 */
public interface ISystemRoleService extends IService<SystemRoleDO> {

    void addRole(SystemRoleRequest param);

    void updateRole(Integer id, SystemRoleRequest param);

    IPage pageList(PageRequest request);

}
