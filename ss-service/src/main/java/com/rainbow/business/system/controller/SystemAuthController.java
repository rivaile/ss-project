package com.rainbow.business.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.rainbow.domain.PageRequest;
import com.rainbow.domain.SystemAuthDO;
import com.rainbow.business.system.service.impl.SystemAuthService;
import com.rainbow.domain.vo.AuthRequest;
import com.rainbow.domain.vo.PageResult;
import com.rainbow.domain.vo.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: lin.deng
 * @version: v1.0
 * @Description:
 * @date: 2019-09-23 11:32
 */
@RestController
@RequestMapping("/system/auth")
public class SystemAuthController {

    @Autowired
    private SystemAuthService authService;


    @PostMapping
    public RestResult addAuth(@RequestBody AuthRequest request) {
        authService.saveAuth(request);
        return RestResult.success();
    }

    @PutMapping("/{id}")
    public RestResult updateAuth(@PathVariable Integer id, @RequestBody AuthRequest request) {
        authService.updateAuth(id, request);
        return RestResult.success(null);
    }

    @GetMapping("/{authModuleId}")
    public PageResult<SystemAuthDO> pageAuthList(@PathVariable Integer authModuleId, PageRequest pageQuery) {

        IPage<SystemAuthDO> page = authService.pageAuthListByModuleId(authModuleId, pageQuery);

        PageResult<SystemAuthDO> pageResult = PageResult.success(page.getRecords());
        pageResult.setTotal(page.getTotal());
        pageResult.setCurrent(page.getCurrent());
        pageResult.setPages(page.getPages());

        return pageResult;
    }

    /**
     * 根据权限去查 角色 和 用户...
     *
     * @param
     */
    @GetMapping("/roleUser/{aclId}")
    public void getRolesAndUsersByAclId(@PathVariable String aclId) {

    }
}
