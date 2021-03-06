package com.rainbow.business.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rainbow.business.system.service.impl.SystemRoleAuthService;
import com.rainbow.business.system.service.impl.SysRoleUserService;
import com.rainbow.business.system.service.impl.SystemRoleService;
import com.rainbow.domain.PageRequest;
import com.rainbow.domain.SystemAuthModuleBO;
import com.rainbow.domain.SystemRoleDO;
import com.rainbow.domain.SystemUserDO;
import com.rainbow.util.StringUtil;
import com.rainbow.domain.vo.PageResult;
import com.rainbow.domain.vo.RestResult;
import com.rainbow.domain.vo.SystemRoleRequest;
import com.rainbow.domain.vo.rsp.SystemRoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    private SystemRoleService systemRoleService;

    @Autowired
    private SystemRoleAuthService systemRoleAuthService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @PostMapping
    public RestResult addRole(@RequestBody @Valid SystemRoleRequest requst) {
        systemRoleService.addRole(requst);
        return RestResult.success();
    }

    @PutMapping("/{id}")
    public RestResult updateRole(
            @PathVariable Integer id,
            @RequestBody @Valid SystemRoleRequest requst) {
        systemRoleService.updateRole(id, requst);
        return RestResult.success();
    }

    @GetMapping
    public PageResult<SystemRoleVO> pageList(PageRequest request) {

        IPage<SystemRoleDO> page = systemRoleService.pageList(request);

        List<SystemRoleVO> pageList = page.getRecords().stream().map(it -> {
            SystemRoleVO response = new SystemRoleVO();
            BeanUtils.copyProperties(it, response);
            return response;
        }).collect(Collectors.toList());

        PageResult<SystemRoleVO> pageResult = PageResult.success(pageList);
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());
        pageResult.setCurrent(page.getCurrent());
        return pageResult;
    }

    /**
     * 权限树...
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public RestResult<List<SystemAuthModuleBO>> authTree(@PathVariable Integer id) {
        return RestResult.success(systemRoleService.roleTreeByRoleId(id));
    }

    /**
     * 角色与权限的对应关系
     *
     * @return
     */
    @PostMapping("/{id}/auth")
    public RestResult changeRoleAuths(@PathVariable Integer id,
                                      @RequestParam(value = "authIds", required = false, defaultValue = "") String authIds) {

        List<Integer> authIdList = StringUtil.splitToListInt(authIds);
        systemRoleAuthService.changeRoleAuths(id, authIdList);
        return RestResult.success();
    }

    /**
     * 角色与用户的对应关系
     *
     * @return
     */
    @PostMapping("/users/{roleId}")
    public RestResult changeRoleUsers(@PathVariable int roleId,
                                      @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
        sysRoleUserService.changeRoleUsers(roleId, userIdList);
        return RestResult.success();
    }

    /**
     * 角色用户
     *
     * @return
     */
    @GetMapping("/users/{roleId}")
    public RestResult<List<SystemUserDO>> users(@PathVariable int roleId) {
        return RestResult.success(sysRoleUserService.getUserListByRoleId(roleId));
    }
}
