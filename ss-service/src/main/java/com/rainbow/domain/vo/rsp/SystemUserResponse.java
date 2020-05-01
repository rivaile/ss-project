package com.rainbow.domain.vo.rsp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel(value = "user请求对象", description = "用户请求user")
@Data
@ToString
public class SystemUserResponse {

    private Long id;

    @ApiModelProperty(value = "用户名", name = "username", example = "shundongmei", required = true)
    private String username;

    @ApiModelProperty(value = "电话", name = "telephone", example = "15112448151", required = true)
    private String telephone;

    private String mail;

    private Integer deptId;

    private Integer status;

    private String remark = "";
}
