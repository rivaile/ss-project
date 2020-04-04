package com.rainbow.vo;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2020-03-10 17:18
 */
public class CommonTreeVo {

    private String title;

    private String value;

    private List<CommonTreeVo> children;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<CommonTreeVo> getChildren() {
        return children;
    }

    public void setChildren(List<CommonTreeVo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "CommonTreeVo{" +
                "title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", children=" + children +
                '}';
    }
}
