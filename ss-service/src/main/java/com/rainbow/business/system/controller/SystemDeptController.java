package com.rainbow.business.system.controller;

import com.rainbow.domain.SystemDeptBO;
import com.rainbow.business.system.service.impl.SystemDeptService;
import com.rainbow.vo.SystemDeptRequest;
import com.rainbow.vo.TreeData;
import com.rainbow.vo.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-26 16:24
 */

@RestController
@RequestMapping("/system/dept")
public class SystemDeptController {

    @Autowired
    private SystemDeptService systemDeptService;

    @PostMapping
    public RestResult addDept(@Valid @RequestBody SystemDeptRequest request) {
        systemDeptService.addDept(request);
        return RestResult.success();
    }


    @DeleteMapping("/{deptId}")
    public RestResult deleteDept(@PathVariable Integer deptId) {
        systemDeptService.deleteDept(deptId);
        return RestResult.success();
    }


    @PutMapping
    public RestResult updateDept(@Valid @RequestBody SystemDeptRequest deptReq) {
        systemDeptService.updateDept(deptReq);
        return RestResult.success();
    }

    @GetMapping
    public RestResult<List<SystemDeptBO>> getDeptListTree() {
        return RestResult.success(systemDeptService.getDeptListTree());
    }

    private List<TreeData> tree(List<SystemDeptBO> list) {
        return list.stream().map(it -> {
            TreeData treeObj = new TreeData();
            treeObj.setTitle(it.getName());
            treeObj.setKey(String.valueOf(it.getId()));
            treeObj.setValue(String.valueOf(it.getId()));
            treeObj.setChildren(tree(it.getChildren()));
            return treeObj;
        }).collect(Collectors.toList());
    }
}
