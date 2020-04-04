package com.rainbow.enums;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2020-04-04 15:18
 */
public enum SystemUserStatus {

    NORMAL(1),
    DELETE(2),
    FREEZE(0);

    private Integer value;

    SystemUserStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
