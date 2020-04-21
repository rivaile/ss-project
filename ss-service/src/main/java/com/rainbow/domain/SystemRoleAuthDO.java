package com.rainbow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("system_role_auth")
public class SystemRoleAuthDO {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer roleId;

    private Integer authId;

    private String operator;

    private Date operateTime;

    private String operateIp;

}
