package com.rainbow.business.system.controller;

import com.rainbow.business.system.service.impl.SystemAuthModuleService;
import com.rainbow.vo.*;
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
@RequestMapping("/system/auth-module")
public class SystemAuthModuleController {

    @Autowired
    private SystemAuthModuleService authModuleService;

    @PostMapping
    public RestResult addAuthModule(@Valid @RequestBody AuthModuleRequest request) {
        authModuleService.addAuthMoudule(request);
        return RestResult.success();
    }

    @DeleteMapping("/{id}")
    public RestResult deleteAuthModule(@PathVariable int id) {
        authModuleService.deleteAuthModule(id);
        return RestResult.success();
    }

    @PutMapping("/{id}")
    public RestResult updateAuthModule(@PathVariable int id,
                                       @Valid @RequestBody AuthModuleRequest request) {
        authModuleService.updateAuthModule(id, request);
        return RestResult.success();
    }

    @GetMapping
    public RestResult<List<AuthModuleTreeData>> getAuthModuleTree() {
        List<AuthModuleTreeData> tree = authModuleService.getSimpleAuthModuleTree();
        return RestResult.success(tree);
    }
}