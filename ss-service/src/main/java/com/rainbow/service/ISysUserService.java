package com.rainbow.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rainbow.domain.SysAclModuleExt;
import com.rainbow.vo.Response;
import com.rainbow.vo.SysUserReq;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-24 15:33
 */
public interface ISysUserService {

    Response<String> addSysUser(SysUserReq userReq);

    boolean deleteUserById(Long userId);

    void updateUser(SysUserReq userReq);

    IPage getSysUser(IPage page, SysUserReq req);

    List<SysAclModuleExt> userAclTree(int userId);
}
