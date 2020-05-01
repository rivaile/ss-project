package com.rainbow.business.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.common.collect.Maps;
import com.rainbow.business.system.service.ISystemUserService;
import com.rainbow.business.system.service.impl.SysRoleUserService;
import com.rainbow.domain.SystemUserDO;
import com.rainbow.domain.vo.*;
import com.rainbow.domain.vo.req.PageSystemUserRequest;
import com.rainbow.domain.vo.req.SystemUserRequest;
import com.rainbow.domain.vo.rsp.SystemUserResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = {"系统用户管理接口"}, value = "系统用户Controller")
@RestController
@RequestMapping("/system/user")
public class SystemUserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISystemUserService systemUserService;

    @Autowired
    private SysRoleUserService sysRoleUserService;

    @ApiOperation(value = "添加用户", notes = "无需传入id")
    @PostMapping
    public RestResult<String> saveUser(@RequestBody SystemUserRequest request) {
        systemUserService.addUser(request);
        return RestResult.success();
    }

    @ApiOperation(value = "修改用户")
    @PutMapping("/{id}")
    public RestResult updateSysUser(
            @PathVariable Long id,
            @RequestBody SystemUserRequest request) {
        systemUserService.updateUser(id, request);
        return RestResult.success();
    }

    @ApiOperation(value = "查找用户")
    @GetMapping
    public PageResult<SystemUserResponse> pageList(PageSystemUserRequest request) {

        IPage<SystemUserDO> pageResult = systemUserService.pageList(request);

        List<SystemUserResponse> pageList = pageResult.getRecords().stream().map(it -> {
            SystemUserResponse response = new SystemUserResponse();
            BeanUtils.copyProperties(it, response);
            return response;
        }).collect(Collectors.toList());

        PageResult<SystemUserResponse> pageRep = PageResult.success(pageList);

        pageRep.setTotal(pageResult.getTotal());
        pageRep.setPages(pageResult.getPages());
        pageRep.setCurrent(pageResult.getCurrent());
        return pageRep;
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{id}")
    public RestResult deleteByUser(@PathVariable Long id) {
        systemUserService.deleteUser(id);
        return RestResult.success();
    }

    /**
     * 根据用户获取权限和角色.
     *
     * @return
     */
    @GetMapping("/acls/{userId}")
    public RestResult<Map<String, Object>> userRolesAndAcls(@PathVariable int userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("acls", systemUserService.userAclTree(userId));
        map.put("roles", sysRoleUserService.getRoleListByUserId(userId));
        return RestResult.success(map);
    }
}
