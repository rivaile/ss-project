package com.rainbow.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.rainbow.domain.PageQuery;
import com.rainbow.domain.SysUser;
import com.rainbow.service.ISysUserService;
import com.rainbow.service.impl.SysRoleUserService;
import com.rainbow.vo.PageResponse;
import com.rainbow.vo.Response;
import com.rainbow.vo.SysUserReq;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @ApiOperation(value = "添加用户")
    @PostMapping
    public Response<String> addSysUser(@Valid @RequestBody SysUserReq req) {
        return sysUserService.addSysUser(req);
    }

    @ApiOperation(value = "修改用户")
    @PutMapping
    public Response updateSysUser(@RequestBody SysUserReq sysUserReq) {
        sysUserService.updateUser(sysUserReq);
        return Response.success();
    }

    @ApiOperation(value = "查找用户")
    @GetMapping
    public PageResponse<SysUser> getUserList(PageQuery pageQuery,
                                             SysUserReq req) {

        Page page = new Page();
        page.setCurrent(pageQuery.getCurrent());
        page.setSize(pageQuery.getPageSize());
        page.setOrders(pageQuery.getOrders());
        IPage userPage = sysUserService.getSysUser(page, req);
        PageResponse<SysUser> pageRep = PageResponse.success(userPage.getRecords());
        pageRep.setTotal(userPage.getTotal());
        pageRep.setPages(userPage.getPages());
        return pageRep;
    }


    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{userId}")
    public Response deleteByUser(@PathVariable Long userId) {
        sysUserService.deleteUserById(userId);
        return Response.success();
    }

    /**
     * 根据用户获取权限和角色.
     *
     * @return
     */
    @GetMapping("/acls/{userId}")
    public Response<Map<String, Object>> userRolesAndAcls(@PathVariable int userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", sysUserService.userAclTree(userId));
        map.put("roles", sysRoleUserService.getRoleListByUserId(userId));
        return Response.success(map);
    }
}
