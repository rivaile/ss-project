package com.rainbow.domain;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-25 11:42
 */
@Data
@ToString(callSuper = true)
public class SystemDeptBO extends SystemDeptDO {

    private List<SystemDeptBO> children = Lists.newArrayList();

}
