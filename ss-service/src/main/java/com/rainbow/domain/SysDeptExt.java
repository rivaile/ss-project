package com.rainbow.domain;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-25 11:42
 */
public class SysDeptExt extends SysDept {

    private List<SysDeptExt> deptList = Lists.newArrayList();

    public List<SysDeptExt> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<SysDeptExt> deptList) {
        this.deptList = deptList;
    }

    @Override
    public String toString() {
        return "SysDeptExt{" +
                "deptList=" + deptList +
                '}';
    }
}
