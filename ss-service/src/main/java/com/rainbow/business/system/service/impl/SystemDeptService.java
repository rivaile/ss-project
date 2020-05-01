package com.rainbow.business.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Preconditions;
import com.rainbow.business.system.dao.SystemDeptMapper;
import com.rainbow.business.system.service.ISystemUserService;
import com.rainbow.domain.SystemDeptDO;
import com.rainbow.domain.SystemDeptBO;
import com.rainbow.domain.SystemUserDO;
import com.rainbow.exception.BusinessException;
import com.rainbow.common.BaseService;
import com.rainbow.business.system.service.ISystemDeptService;
import com.rainbow.util.LevelUtil;
import com.rainbow.domain.vo.SystemDeptRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.rainbow.common.Constant.ROOT;
import static com.rainbow.common.Constant.SEPARATOR;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-25 11:21
 */
@Service
@Slf4j
public class SystemDeptService extends BaseService<SystemDeptMapper, SystemDeptDO> implements ISystemDeptService {

    @Autowired
    private ISystemUserService systemUserService;

    @Override
    public void addDept(SystemDeptRequest param) {

        int count = count(new QueryWrapper<SystemDeptDO>().lambda()
                .eq(SystemDeptDO::getParentId, param.getParentId())
                .eq(SystemDeptDO::getName, param.getName()));
        if (count > 0) {
            throw new BusinessException("同一层级下存在相同名称的部门");
        }

        SystemDeptDO dept = new SystemDeptDO();
        BeanUtils.copyProperties(param, dept);

        SystemDeptDO parentDept = getById(param.getParentId());
        String parentLevel = parentDept == null ? null : parentDept.getLevel();
        String level = LevelUtil.calculateLevel(parentLevel, param.getParentId());

        dept.setLevel(level);
        dept.setOperator("system");
        dept.setOperateIp("127.0.0.1");
        save(dept);
    }


    @Override
    public void deleteDept(Integer deptId) {

        Preconditions.checkNotNull(getById(deptId), "待删除的部门不存在，无法删除!");

        int count = count(new QueryWrapper<SystemDeptDO>().lambda()
                .eq(SystemDeptDO::getParentId, deptId));
        if (count > 0) throw new BusinessException("当前部门下面有子部门，无法删除!");

        int userCount = systemUserService.count(new QueryWrapper<SystemUserDO>().lambda()
                .eq(SystemUserDO::getDeptId, deptId));
        if (userCount > 0) throw new BusinessException("当前部门下面有用户，无法删除!");

        removeById(deptId);
    }

    @Override
    public void updateDept(SystemDeptRequest param) {

        SystemDeptDO before = getById(param.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");

        int count = count(new QueryWrapper<SystemDeptDO>().lambda()
                .eq(SystemDeptDO::getParentId, param.getParentId())
                .eq(SystemDeptDO::getName, param.getName()));
        if (count > 0) throw new BusinessException("同一层级下存在相同名称的部门");

        SystemDeptDO after = new SystemDeptDO();
        BeanUtils.copyProperties(param, after);

        String level = LevelUtil.calculateLevel(getById(after.getParentId()).getLevel(), after.getParentId());
        after.setLevel(level);
        after.setOperator("system");
        after.setOperateIp("127.0.0.1");

        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();

        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            List<SystemDeptDO> deptList = list(new QueryWrapper<SystemDeptDO>().lambda()
                    .likeRight(SystemDeptDO::getLevel, oldLevelPrefix + "."));
            if (CollectionUtils.isNotEmpty(deptList)) {
                deptList.forEach(it -> it.setLevel(newLevelPrefix + it.getLevel().substring(oldLevelPrefix.length())));
                updateBatchById(deptList);
            }
        }

        updateById(after);
    }

    @Override
    public List<SystemDeptBO> getDeptListTree() {

        List<SystemDeptDO> deptList = list();

        List<SystemDeptBO> rootDept = deptList.stream()
                .filter(it -> it.getLevel().equals(ROOT))
                .sorted(Comparator.comparingInt(SystemDeptDO::getSeq))
                .map(it -> {
                    SystemDeptBO dept = new SystemDeptBO();
                    BeanUtils.copyProperties(it, dept);
                    return dept;
                }).collect(Collectors.toList());

        Map<String, List<SystemDeptBO>> levelDeptMap = deptList.stream().map(it -> {
            SystemDeptBO deptExt = new SystemDeptBO();
            BeanUtils.copyProperties(it, deptExt);
            return deptExt;
        }).collect(Collectors.groupingBy(SystemDeptBO::getLevel));

        deptList2Tree(rootDept, ROOT, levelDeptMap);

        return rootDept;
    }


    public void deptList2Tree(List<SystemDeptBO> deptList, String level, Map<String, List<SystemDeptBO>> levelDeptMap) {
        deptList.stream().forEach(it -> {
            String nextLevel = level + SEPARATOR + it.getId();
            List<SystemDeptBO> nextDeptList = levelDeptMap.get(nextLevel);

            if (CollectionUtils.isNotEmpty(nextDeptList)) {
                Collections.sort(nextDeptList, Comparator.comparingInt(SystemDeptDO::getSeq));
                it.setChildren(nextDeptList);
                deptList2Tree(nextDeptList, nextLevel, levelDeptMap);
            }
        });
    }
}
