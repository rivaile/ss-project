package com.rainbow.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
@TableName("sys_role")
public class SystemRole {

    private Integer id;

    private String name;

    private Integer type;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;

}