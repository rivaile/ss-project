package com.rainbow.business.system.service;

import com.rainbow.domain.SystemDeptBO;
import com.rainbow.vo.SystemDeptRequest;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-25 11:22
 */
public interface ISystemDeptService {

    void addDept(SystemDeptRequest deptReq);

    void deleteDept(Integer deptId);

    void updateDept(SystemDeptRequest deptReq);

    /**
     * 第一种:全部查询出来,第二种:点击查询.
     *
     * @return
     */
    List<SystemDeptBO> getDeptListTree();

}
