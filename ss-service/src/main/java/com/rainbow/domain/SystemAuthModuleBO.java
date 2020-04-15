package com.rainbow.domain;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-30 18:52
 */
@Data
@ToString
public class SystemAuthModuleBO extends SystemAuthModuleDO {

    /**
     * 下级模块
     */
    private List<SystemAuthModuleBO> moduleList = Lists.newArrayList();
    /**
     * 挂载权限
     */
    private List<SystemAuthExt> authList = Lists.newArrayList();

}
