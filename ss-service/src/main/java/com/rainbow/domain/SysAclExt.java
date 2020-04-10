package com.rainbow.domain;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-30 18:52
 */
public class SysAclExt extends SystemAuthDO {

    // 是否要默认选中
    private boolean checked = false;
    // 是否有权限操作
    private boolean hasAcl = false;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean isHasAcl() {
        return hasAcl;
    }

    public void setHasAcl(boolean hasAcl) {
        this.hasAcl = hasAcl;
    }
}
