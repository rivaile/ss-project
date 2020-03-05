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

    private List<SysDeptExt> children = Lists.newArrayList();

    public List<SysDeptExt> getChildren() {
        return children;
    }

    public void setChildren(List<SysDeptExt> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "SysDeptExt{" +
                "children=" + children +
                '}';
    }
}
