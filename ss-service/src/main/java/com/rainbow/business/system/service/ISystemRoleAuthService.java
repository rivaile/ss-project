package com.rainbow.business.system.service;

import java.util.List;

public interface ISystemRoleAuthService {
    void changeRoleAuths(int roleId, List<Integer> authIdList);
}
