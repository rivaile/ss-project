package com.rainbow.business.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rainbow.domain.PageRequest;
import com.rainbow.domain.SysAcl;
import com.rainbow.business.system.service.impl.SysAclService;
import com.rainbow.vo.AclParam;
import com.rainbow.vo.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-23 11:32
 */

@RestController
@RequestMapping("/sys/acl")
public class SysAclController {

    @Autowired
    private SysAclService sysAclService;

    @PostMapping
    public RestResult addAcl(@RequestBody AclParam param) {
        sysAclService.saveAcl(param);
        return RestResult.success(null);
    }

    @PutMapping
    public RestResult updateAcl(@RequestBody AclParam param) {
        sysAclService.updateAcl(param);
        return RestResult.success(null);
    }

    @GetMapping("/{aclModuleId}")
    public RestResult<IPage<SysAcl>> getAclList(@PathVariable Integer aclModuleId, PageRequest pageQuery) {
        Page page = new Page();
        page.setCurrent(pageQuery.getCurrent());
        page.setSize(pageQuery.getPageSize());
        page.setOrders(pageQuery.getOrders());
        return RestResult.success(sysAclService.getAclListByModuleId(aclModuleId, page));
    }

    /**
     * 根据权限去查 角色 和 用户...
     *
     * @param acl
     */
    @GetMapping("/roleUser/{aclId}")
    public void getRolesAndUsersByAclId(@PathVariable String aclId) {

    }
}
