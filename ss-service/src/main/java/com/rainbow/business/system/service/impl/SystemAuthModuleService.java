package com.rainbow.business.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.rainbow.business.system.dao.SysAclMapper;
import com.rainbow.business.system.dao.SysAclModuleMapper;
import com.rainbow.domain.SystemAuthDO;
import com.rainbow.domain.SystemAuthModuleDO;
import com.rainbow.domain.SystemAuthModuleBO;
import com.rainbow.enums.ReturnCode;
import com.rainbow.exception.BusinessException;
import com.rainbow.common.BaseService;
import com.rainbow.business.system.service.ISystemAuthModuleService;
import com.rainbow.util.LevelUtil;
import com.rainbow.vo.AuthModuleRequest;
import com.rainbow.vo.TreeData;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.rainbow.common.Constant.ROOT;
import static com.rainbow.common.Constant.SEPARATOR;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-10-10 20:40
 */
@Service
public class SystemAuthModuleService extends BaseService<SysAclModuleMapper, SystemAuthModuleDO> implements ISystemAuthModuleService {

    @Autowired
    private SysAclMapper sysAclMapper;

    @Override
    public void addAuthMoudule(AuthModuleRequest param) {

        statusVerify(param);
        SystemAuthModuleDO authModule = new SystemAuthModuleDO();
        BeanUtils.copyProperties(param, authModule);
        authModule.setOperateIp("127.0.0.1");
        save(authModule);
    }


    @Override
    public void deleteAuthModule(int id) {
        SystemAuthModuleDO authModule = getById(id);
        Preconditions.checkNotNull(authModule, "待删除的权限模块不存在，无法删除!");
        int count = count(new QueryWrapper<SystemAuthModuleDO>().lambda()
                .eq(SystemAuthModuleDO::getParentId, id));
        if (count > 0) {
            throw new BusinessException("当前模块下面有子模块，无法删除!");
        }

        if (sysAclMapper.selectCount(new QueryWrapper<SystemAuthDO>().lambda()
                .eq(SystemAuthDO::getAclModuleId, id)) > 0) {
            throw new BusinessException("当前模块下面挂载有权限，无法删除!");
        }

        removeById(id);
    }

    @Override
    public void updateAuthModule(int id, AuthModuleRequest param) {

        SystemAuthModuleDO before = getById(id);
        Preconditions.checkNotNull(before, "待更新的权限模块不存在!");

        statusVerify(param);

        SystemAuthModuleDO after = new SystemAuthModuleDO();
        BeanUtils.copyProperties(param, after);

        SystemAuthModuleDO parent = getById(param.getParentId());
        after.setLevel(LevelUtil.calculateLevel(parent != null ? parent.getLevel() : null, parent.getId()));
        after.setOperator("sys");
        after.setOperateIp("127.0.0.1");

        updateWithChild(before, after);
    }

    @Override
    public List<SystemAuthModuleBO> getAuthModuleTree() {

        List<SystemAuthModuleBO> moduleList = list().stream().map(it -> {
            SystemAuthModuleBO target = new SystemAuthModuleBO();
            BeanUtils.copyProperties(it, target);
            return target;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(moduleList)) {
            return Lists.newArrayList();
        }

        Multimap<String, SystemAuthModuleBO> moduleLevelMap = ArrayListMultimap.create();
        List<SystemAuthModuleBO> roots = Lists.newArrayList();

        moduleList.forEach(it -> {
            moduleLevelMap.put(it.getLevel(), it);
            if (ROOT.equals(it.getLevel())) {
                roots.add(it);
            }
        });
        Collections.sort(roots, Comparator.comparingInt(SystemAuthModuleDO::getSeq));

        transformModuleTree(roots, ROOT, moduleLevelMap);
        return roots;
    }

    @Override
    public List<TreeData> getSimpleAuthModuleTree() {
        return simpleTree(getAuthModuleTree());
    }

    private List<TreeData> simpleTree(List<SystemAuthModuleBO> list) {
        return list.stream().map(it -> {
            TreeData treeObj = new TreeData();
            treeObj.setTitle(it.getName());
            treeObj.setKey(String.valueOf(it.getId()));
            treeObj.setChildren(simpleTree(it.getModuleList()));
            return treeObj;
        }).collect(Collectors.toList());
    }

    private void transformModuleTree(List<SystemAuthModuleBO> roots, String parentLevel, Multimap<String, SystemAuthModuleBO> moduleLevelMap) {
        roots.forEach(it -> {
            String nextLevel = parentLevel + SEPARATOR + it.getId();
            List<SystemAuthModuleBO> tempList = (List<SystemAuthModuleBO>) moduleLevelMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)) {
                Collections.sort(tempList, Comparator.comparingInt(SystemAuthModuleDO::getSeq));
                it.setModuleList(tempList);
                transformModuleTree(tempList, nextLevel, moduleLevelMap);
            }
        });
    }

    @Transactional
    void updateWithChild(SystemAuthModuleDO before, SystemAuthModuleDO after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SystemAuthModuleDO> aclModuleList = list(new QueryWrapper<SystemAuthModuleDO>().lambda()
                    .like(SystemAuthModuleDO::getLevel, oldLevelPrefix + ".%"));
            if (CollectionUtils.isNotEmpty(aclModuleList)) {
                aclModuleList.forEach(it -> {
                    String level = it.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        it.setLevel(level);
                    }
                });
                updateBatchById(aclModuleList);
            }
            updateById(after);
        }
    }


    private void statusVerify(AuthModuleRequest param) {
        int count = count(new QueryWrapper<SystemAuthModuleDO>().lambda()
                .eq(SystemAuthModuleDO::getName, param.getName())
                .eq(SystemAuthModuleDO::getParentId, param.getParentId())
                .eq(param.getId() != null, SystemAuthModuleDO::getId, param.getId()));
        if (count > 0) {
            throw new BusinessException(ReturnCode.VERIFY_EXSIT_ERROR, "同一层级下存在相同名称的权限模块!");
        }
    }

}