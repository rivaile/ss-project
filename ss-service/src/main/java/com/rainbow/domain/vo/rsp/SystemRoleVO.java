package com.rainbow.domain.vo.rsp;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@ApiModel(value = "user请求对象", description = "用户请求user")
@Data
@ToString
public class SystemRoleVO {

    private Integer id;

    private String name;

    private Integer type;

    private Integer status;

    private String remark;

    private String operator;

    private Date operateTime;

    private String operateIp;
    
}
