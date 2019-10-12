package com.rainbow.web.controller;

import com.rainbow.domain.SysAclModuleExt;
import com.rainbow.domain.SysRole;
import com.rainbow.domain.SysUser;
import com.rainbow.service.impl.SysRoleAclService;
import com.rainbow.service.impl.SysRoleService;
import com.rainbow.service.impl.SysRoleUserService;
import com.rainbow.util.StringUtil;
import com.rainbow.vo.Response;
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
@RequestMapping("/sys/role")
public class SysRoleController {

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private SysRoleAclService sysRoleAclService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @PostMapping
    public Response addRole(@RequestBody @Valid SysRoleReq req) {
        roleService.save(req);
        return Response.success(null);
    }

    @PutMapping
    public Response updateRole(@RequestBody @Valid SysRoleReq req) {
        roleService.update(req);
        return Response.success(null);
    }

    @GetMapping
    public Response<List<SysRole>> getAll() {
        return Response.success(roleService.list());
    }

    /**
     * 权限树...
     *
     * @param id
     * @return
     */
    @GetMapping("/{id:d+}")
    public Response<List<SysAclModuleExt>> roleTree(@PathVariable int id) {
        return Response.success(roleService.roleTreeByRoleId(id));
    }

    /**
     * 角色与权限的对应关系
     *
     * @return
     */
    @PostMapping("/acls/{roleId}")
    public Response<Object> changeRoleAcls(@PathVariable int roleId,
                                           @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {

        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);

        sysRoleAclService.changeRoleAcls(roleId, aclIdList);

        return Response.success(null);
    }

    /**
     * 角色与用户的对应关系
     *
     * @return
     */
    @PostMapping("/users/{roleId}")
    public Response<Object> changeRoleUsers(@PathVariable int roleId,
                                            @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        sysRoleUserService.changeRoleUsers(roleId, userIdList);
        return Response.success(null);
    }

    /**
     * 角色用户
     *
     * @return
     */
    @GetMapping("/users/{roleId}")
    public Response<List<SysUser>> users(@PathVariable int roleId) {
        return Response.success(sysRoleUserService.getUserListByRoleId(roleId));
    }
}
