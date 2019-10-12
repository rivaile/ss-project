package com.rainbow.web.controller;

import com.rainbow.domain.SysAclModuleExt;
import com.rainbow.service.impl.SysAclModuleService;
import com.rainbow.util.BeanValidator;
import com.rainbow.vo.AclModuleParam;
import com.rainbow.vo.Response;
import com.rainbow.vo.SysDeptReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-10-08 20:31
 */
@RestController
@RequestMapping("/sys/aclModule")
public class SysAclModuleController {

    @Autowired
    private SysAclModuleService aclModuleService;

    @PostMapping
    public Response addAclModule(@Valid @RequestBody AclModuleParam param) {
        aclModuleService.saveAclMoudule(param);
        return Response.success(null);
    }

    @DeleteMapping("/{id}")
    public Response delAclModule(@PathVariable int id) {
        aclModuleService.delAclModule(id);
        return Response.success(null);
    }

    @PutMapping
    public Response updateAclModule(@Valid @RequestBody AclModuleParam param) {
        aclModuleService.updateAclModule(param);
        return Response.success(null);
    }

    @GetMapping
    public Response<List<SysAclModuleExt>> getAclModuleTree() {
        return Response.success(aclModuleService.aclModuleTree());
    }
    
}