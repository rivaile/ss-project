package com.rainbow.business.system.controller;

import com.rainbow.domain.SysAclModuleExt;
import com.rainbow.domain.SystemRole;
import com.rainbow.domain.SystemUser;
import com.rainbow.business.system.service.impl.SysRoleAclService;
import com.rainbow.business.system.service.impl.SysRoleService;
import com.rainbow.business.system.service.impl.SysRoleUserService;
import com.rainbow.util.StringUtil;
import com.rainbow.vo.RestResult;
import com.rainbow.vo.SysRoleReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-23 11:29
 */

@RestController
@RequestMapping("/system/role")
public class SystemRoleController {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @PostMapping
    public RestResult addRole(@RequestBody @Valid SysRoleReq req) {
        roleService.save(req);
        return RestResult.success(null);
    }

    @PutMapping
    public RestResult updateRole(@RequestBody @Valid SysRoleReq req) {
        roleService.update(req);
        return RestResult.success(null);
    }

    @GetMapping
    public RestResult<List<SystemRole>> getAll() {
        return RestResult.success(roleService.list());
    }

    /**
     * 权限树...
     *
     * @param id
     * @return
     */
    @GetMapping("/{id:d+}")
    public RestResult<List<SysAclModuleExt>> roleTree(@PathVariable int id) {
        return RestResult.success(roleService.roleTreeByRoleId(id));
    }

    /**
     * 角色与权限的对应关系
     *
     * @return
     */
    @PostMapping("/acls/{roleId}")
    public RestResult<Object> changeRoleAcls(@PathVariable int roleId,
                                             @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {

        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);

        sysRoleAclService.changeRoleAcls(roleId, aclIdList);

        return RestResult.success(null);
    }

    /**
     * 角色与用户的对应关系
     *
     * @return
     */
    @PostMapping("/users/{roleId}")
    public RestResult<Object> changeRoleUsers(@PathVariable int roleId,
                                              @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        sysRoleUserService.changeRoleUsers(roleId, userIdList);
        return RestResult.success(null);
    }

    /**
     * 角色用户
     *
     * @return
     */
    @GetMapping("/users/{roleId}")
    public RestResult<List<SystemUser>> users(@PathVariable int roleId) {
        return RestResult.success(sysRoleUserService.getUserListByRoleId(roleId));
    }
}
