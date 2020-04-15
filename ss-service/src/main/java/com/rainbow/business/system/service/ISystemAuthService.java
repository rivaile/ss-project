package com.rainbow.business.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rainbow.domain.PageRequest;
import com.rainbow.domain.SystemAuthDO;
import com.rainbow.vo.AuthRequest;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-24 15:33
 */
public interface ISystemAuthService {

    void saveAuth(AuthRequest request);

    void updateAuth(Integer id, AuthRequest param);

    IPage<SystemAuthDO> pageAuthListByModuleId(Integer authModuleId, PageRequest pageRequest);
}
