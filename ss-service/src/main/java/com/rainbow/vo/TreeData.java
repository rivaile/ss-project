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
public class TreeData {

    private String title;

    private String value;

    private String key;

    private Boolean checked;

    private Boolean disabled;

    private List<TreeData> children;
}
