package com.rainbow.web.controller;

import com.rainbow.domain.SysDeptExt;
import com.rainbow.service.impl.SysDeptService;
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
 * @date: 2019-09-26 16:24
 */

@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @PostMapping
    public Response addDept(@Valid @RequestBody SysDeptReq deptReq) {
        sysDeptService.addDept(deptReq);
        return Response.success(null);
    }


    @DeleteMapping("/{deptId}")
    public Response deleteDept(@PathVariable Integer deptId) {
        sysDeptService.deleteDept(deptId);
        return Response.success(null);
    }


    @PutMapping
    public Response updateDept(@Valid @RequestBody SysDeptReq deptReq) {
        sysDeptService.updateDept(deptReq);
        return Response.success(null);
    }


    @GetMapping
    public Response<List<SysDeptExt>> getDeptListTree() {
        return Response.success(sysDeptService.getDeptListTree());
    }

}
