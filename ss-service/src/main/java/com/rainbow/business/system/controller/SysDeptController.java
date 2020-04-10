package com.rainbow.business.system.controller;

import com.rainbow.domain.SysDeptExt;
import com.rainbow.business.system.service.impl.SysDeptService;
import com.rainbow.vo.TreeData;
import com.rainbow.vo.RestResult;
import com.rainbow.vo.SysDeptReq;
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
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @PostMapping
    public RestResult addDept(@Valid @RequestBody SysDeptReq deptReq) {
        sysDeptService.addDept(deptReq);

        RestResult<Object> response = RestResult.success(null);
        return response;
    }


    @DeleteMapping("/{deptId}")
    public RestResult deleteDept(@PathVariable Long deptId) {
        sysDeptService.deleteDept(deptId);
        return RestResult.success(null);
    }


    @PutMapping
    public RestResult updateDept(@Valid @RequestBody SysDeptReq deptReq) {
        sysDeptService.updateDept(deptReq);
        return RestResult.success(null);
    }


    @GetMapping
    public List<TreeData> getDeptListTree() {
        return tree(sysDeptService.getDeptListTree());
    }

    private List<TreeData> tree(List<SysDeptExt> list) {
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
