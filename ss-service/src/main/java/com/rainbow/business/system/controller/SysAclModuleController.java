package com.rainbow.business.system.controller;

import com.rainbow.domain.SysAclModuleExt;
import com.rainbow.business.system.service.impl.SysAclModuleService;
import com.rainbow.vo.AclModuleParam;
import com.rainbow.vo.RestResult;
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
    public RestResult addAclModule(@Valid @RequestBody AclModuleParam param) {
        aclModuleService.saveAclMoudule(param);
        return RestResult.success(null);
    }

    @DeleteMapping("/{id}")
    public RestResult delAclModule(@PathVariable int id) {
        aclModuleService.delAclModule(id);
        return RestResult.success(null);
    }

    @PutMapping
    public RestResult updateAclModule(@Valid @RequestBody AclModuleParam param) {
        aclModuleService.updateAclModule(param);
        return RestResult.success(null);
    }

    @GetMapping
    public RestResult<List<SysAclModuleExt>> getAclModuleTree() {
        return RestResult.success(aclModuleService.aclModuleTree());
    }
    
}