package com.rainbow.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-30 19:02
 */
@Data
@ToString
public class SysRoleAcl {

    private Integer id;

    private Integer roleId;

    private Integer aclId;

    private String operator;

    private Date operateTime;

    private String operateIp;

}
