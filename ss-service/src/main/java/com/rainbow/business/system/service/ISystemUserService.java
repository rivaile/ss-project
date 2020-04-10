package com.rainbow.business.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbow.domain.SystemAuthModuleBO;
import com.rainbow.domain.SystemUserDO;
import com.rainbow.vo.req.PageSystemUserRequest;
import com.rainbow.vo.req.SystemUserRequest;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-24 15:33
 */
public interface ISystemUserService extends IService<SystemUserDO> {

    boolean addUser(SystemUserRequest request);

    boolean deleteUser(Long userId);

    boolean updateUser(Long id, SystemUserRequest request);

    IPage pageList(PageSystemUserRequest request);

    List<SystemAuthModuleBO> userAclTree(int userId);
}
