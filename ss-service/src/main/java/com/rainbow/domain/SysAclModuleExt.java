package com.rainbow.domain;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-30 18:52
 */
public class SysAclModuleExt extends SysAclModule {

    /**
     * 下级模块
     */
    private List<SysAclModuleExt> aclModuleList = Lists.newArrayList();
    /**
     * 挂载权限
     */
    private List<SysAclExt> aclList = Lists.newArrayList();

    public List<SysAclModuleExt> getAclModuleList() {
        return aclModuleList;
    }

    public void setAclModuleList(List<SysAclModuleExt> aclModuleList) {
        this.aclModuleList = aclModuleList;
    }

    public List<SysAclExt> getAclList() {
        return aclList;
    }

    public void setAclList(List<SysAclExt> aclList) {
        this.aclList = aclList;
    }

}
