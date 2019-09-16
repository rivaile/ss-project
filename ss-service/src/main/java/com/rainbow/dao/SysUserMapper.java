package com.rainbow.dao;

import com.rainbow.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserMapper {

    Integer insert(SysUser record);

}
