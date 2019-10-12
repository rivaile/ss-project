package com.rainbow.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rainbow.domain.PageQuery;
import com.rainbow.domain.SysAcl;
import com.rainbow.service.impl.SysAclService;
import com.rainbow.vo.AclParam;
import com.rainbow.vo.Response;
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
    public Response addAcl(@RequestBody AclParam param) {
        sysAclService.saveAcl(param);
        return Response.success(null);
    }

    @PutMapping
    public Response updateAcl(@RequestBody AclParam param) {
        sysAclService.updateAcl(param);
        return Response.success(null);
    }

    @GetMapping("/{aclModuleId}")
    public Response<IPage<SysAcl>> getAclList(@PathVariable Integer aclModuleId, PageQuery pageQuery) {
        Page page = new Page();
        page.setCurrent(pageQuery.getCurrent());
        page.setSize(pageQuery.getSize());
        page.setOrders(pageQuery.getOrders());
        return Response.success(sysAclService.getAclListByModuleId(aclModuleId, page));
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
