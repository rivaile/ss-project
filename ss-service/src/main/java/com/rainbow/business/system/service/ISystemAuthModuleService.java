package com.rainbow.business.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbow.domain.SystemAuthModuleBO;
import com.rainbow.domain.SystemAuthModuleDO;
import com.rainbow.vo.AuthModuleRequest;
import com.rainbow.vo.AuthModuleTreeData;
import com.rainbow.vo.TreeData;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-24 15:33
 */
public interface ISystemAuthModuleService extends IService<SystemAuthModuleDO> {

    void addAuthMoudule(AuthModuleRequest request);

    void deleteAuthModule(int id);

    void updateAuthModule(int id, AuthModuleRequest param);

    List<SystemAuthModuleBO> getAuthModuleTree();

    List<AuthModuleTreeData> getSimpleAuthModuleTree();
}
