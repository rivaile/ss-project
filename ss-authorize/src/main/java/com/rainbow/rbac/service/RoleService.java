package com.rainbow.rbac.service;


import com.rainbow.rbac.dto.RoleInfo;

import java.util.List;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: RoleService
 * @date 2018/9/25 17:11
 */
public interface RoleService {

    /**
     * 创建角色
     * @param roleInfo
     * @return
     */
    RoleInfo create(RoleInfo roleInfo);
    /**
     * 修改角色
     * @param roleInfo
     * @return
     */
    RoleInfo update(RoleInfo roleInfo);
    /**
     * 删除角色
     * @param id
     */
    void delete(Long id);
    /**
     * 获取角色详细信息
     * @param id
     * @return
     */
    RoleInfo getInfo(Long id);
    /**
     * 查询所有角色
     * @param
     * @return
     */
    List<RoleInfo> findAll();
    /**
     * @param id
     * @return
     */
    String[] getRoleResources(Long id);
    /**
     * @param id
     * @param ids
     */
    void setRoleResources(Long id, String ids);

}
