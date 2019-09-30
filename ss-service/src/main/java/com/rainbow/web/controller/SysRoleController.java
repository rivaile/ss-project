package com.rainbow.web.controller;

import com.rainbow.domain.SysRole;
import com.rainbow.service.impl.SysRoleService;
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



}
