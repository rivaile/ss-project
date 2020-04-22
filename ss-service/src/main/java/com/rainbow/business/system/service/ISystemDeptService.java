package com.rainbow.business.system.service;

import com.rainbow.domain.SysDeptExt;
import com.rainbow.vo.SysDeptReq;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-25 11:22
 */
public interface ISystemDeptService {

    void addDept(SysDeptReq deptReq);

    void deleteDept(Long deptId);

    void updateDept(SysDeptReq deptReq);

    /**
     * 第一种:全部查询出来,第二种:点击查询.
     *
     * @return
     */
    List<SysDeptExt> getDeptListTree();

}
