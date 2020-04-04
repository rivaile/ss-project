package com.rainbow.business.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbow.domain.SysAclModuleExt;
import com.rainbow.domain.SystemUser;
import com.rainbow.vo.req.PageSystemUserRequest;
import com.rainbow.vo.req.SystemUserRequest;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-24 15:33
 */
public interface ISystemUserService extends IService<SystemUser> {

    boolean addUser(SystemUserRequest request);

    boolean deleteUser(Long userId);

    boolean updateUser(Long id, SystemUserRequest request);

    IPage pageList(PageSystemUserRequest request);

    List<SysAclModuleExt> userAclTree(int userId);
}
