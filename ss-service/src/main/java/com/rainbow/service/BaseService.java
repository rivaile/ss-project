package com.rainbow.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author: denglin
 * @version: v1.0
 * @Description: IBaseService 实现类
 * @date: 2019-09-19 17:01
 */
public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements IBaseService<T> {

}
