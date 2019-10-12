package com.rainbow.util;


import org.apache.commons.lang.StringUtils;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-10-11 16:29
 */
public class LevelUtil {

    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    // 0
    // 0.1
    // 0.1.2
    // 0.1.3
    // 0.4
    public static String calculateLevel(String parentLevel, int parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return parentLevel + SEPARATOR + parentId;
        }
    }
}
