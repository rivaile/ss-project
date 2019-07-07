package com.rainbow.rbac.dto;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: RoleInfo
 * @date 2018/9/25 17:12
 */
public class RoleInfo {
    private Long id;

    private String name;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
