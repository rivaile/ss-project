package com.rainbow.business.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Preconditions;
import com.rainbow.business.system.dao.SystemDeptMapper;
import com.rainbow.business.system.dao.SystemUserMapper;
import com.rainbow.domain.SystemDeptDO;
import com.rainbow.domain.SysDeptExt;
import com.rainbow.domain.SystemUserDO;
import com.rainbow.exception.BusinessException;
import com.rainbow.common.BaseService;
import com.rainbow.business.system.service.ISystemDeptService;
import com.rainbow.vo.SysDeptReq;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-25 11:21
 */
@Service
public class SystemDeptService extends BaseService<SystemDeptMapper, SystemDeptDO> implements ISystemDeptService {

    private static final String ROOT = "0";

    private final static String SEPARATOR = ".";

    @Autowired
    private SystemUserMapper userMapper;

    @Override
    public void addDept(SysDeptReq deptReq) {

        if (baseMapper.selectCount(new QueryWrapper<SystemDeptDO>()
                .lambda()
                .eq(SystemDeptDO::getParentId, deptReq.getParentId())
                .eq(SystemDeptDO::getName, deptReq.getName())) > 0)
            throw new BusinessException("同一层级下存在相同名称的部门");

        SystemDeptDO dept = new SystemDeptDO();
        BeanUtils.copyProperties(deptReq, dept);
        dept.setLevel(getById(dept.getParentId()).getLevel()
                + SEPARATOR + dept.getParentId());
        dept.setOperator("system");
        dept.setOperateIp("127.0.0.1");

        save(dept);
    }

    @Override
    public void deleteDept(Long deptId) {

        Preconditions.checkNotNull(getById(deptId), "待删除的部门不存在，无法删除!");
        if (baseMapper.selectCount(new QueryWrapper<SystemDeptDO>()
                .lambda().eq(SystemDeptDO::getParentId, deptId)) > 0)
            throw new BusinessException("当前部门下面有子部门，无法删除!");

        if (userMapper.selectCount(new QueryWrapper<SystemUserDO>()
                .lambda().eq(SystemUserDO::getDeptId, deptId)) > 0)
            throw new BusinessException("当前部门下面有用户，无法删除!");

        removeById(deptId);
    }

    @Override
    public void updateDept(SysDeptReq deptReq) {

        SystemDeptDO before = getById(deptReq.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");

        if (baseMapper.selectCount(new QueryWrapper<SystemDeptDO>()
                .lambda()
                .eq(SystemDeptDO::getParentId, deptReq.getParentId())
                .eq(SystemDeptDO::getName, deptReq.getName())) > 0)
            throw new BusinessException("同一层级下存在相同名称的部门");

        SystemDeptDO after = new SystemDeptDO();
        BeanUtils.copyProperties(before, after);
        after.setLevel(getById(after.getParentId()).getLevel()
                + SEPARATOR + after.getParentId());
        after.setOperator("system");
        after.setOperateIp("127.0.0.1");

        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();

        if (!newLevelPrefix.equals(oldLevelPrefix)) {
            List<SystemDeptDO> deptList = list(new QueryWrapper<SystemDeptDO>()
                    .lambda().like(SystemDeptDO::getLevel, oldLevelPrefix + " || .%"));
            if (CollectionUtils.isNotEmpty(deptList)) {
                deptList.forEach(it -> it.setLevel(newLevelPrefix + it.getLevel().substring(oldLevelPrefix.length())));
                updateBatchById(deptList);
            }
        }

        updateById(after);
    }

    @Override
    public List<SysDeptExt> getDeptListTree() {

        List<SystemDeptDO> deptList = list();

        List<SysDeptExt> rootDept = deptList.stream()
                .filter(it -> it.getLevel().equals(ROOT))
                .sorted(Comparator.comparingInt(SystemDeptDO::getSeq))
                .map(it -> {
                    SysDeptExt deptExt = new SysDeptExt();
                    BeanUtils.copyProperties(it, deptExt);
                    return deptExt;
                }).collect(Collectors.toList());

        Map<String, List<SysDeptExt>> levelDeptMap = deptList.stream().map(it -> {
            SysDeptExt deptExt = new SysDeptExt();
            BeanUtils.copyProperties(it, deptExt);
            return deptExt;
        }).collect(Collectors.groupingBy(SysDeptExt::getLevel));

        deptList2Tree(rootDept, ROOT, levelDeptMap);

        return rootDept;
    }


    public void deptList2Tree(List<SysDeptExt> deptList, String level, Map<String, List<SysDeptExt>> levelDeptMap) {
        deptList.stream().forEach(it -> {
            String nextLevel = level + SEPARATOR + it.getId();
            List<SysDeptExt> nextDeptList = levelDeptMap.get(nextLevel);

            if (CollectionUtils.isNotEmpty(nextDeptList)) {
                Collections.sort(nextDeptList, Comparator.comparingInt(SystemDeptDO::getSeq));
                it.setChildren(nextDeptList);
                deptList2Tree(nextDeptList, nextLevel, levelDeptMap);
            }
        });
    }
}
