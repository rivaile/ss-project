package com.rainbow.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2020-03-10 17:18
 */
@Data
@ToString
public class AuthModuleTreeData {

    private String title;
    private String value;
    private String key;

    private Integer id;
    private Integer parentId;
    private String name;
    private Integer seq;
    private Integer status;
    private String remark;

    private Boolean checked;
    private Boolean disabled;
    private List<AuthModuleTreeData> children;
}
