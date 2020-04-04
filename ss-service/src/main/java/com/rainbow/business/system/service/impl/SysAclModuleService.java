package com.rainbow.business.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.rainbow.business.system.dao.SysAclMapper;
import com.rainbow.business.system.dao.SysAclModuleMapper;
import com.rainbow.domain.SysAcl;
import com.rainbow.domain.SysAclModule;
import com.rainbow.domain.SysAclModuleExt;
import com.rainbow.exception.BusinessException;
import com.rainbow.common.BaseService;
import com.rainbow.business.system.service.ISysAclModuleService;
import com.rainbow.util.BeanValidator;
import com.rainbow.util.LevelUtil;
import com.rainbow.vo.AclModuleParam;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
public class SysAclModuleService extends BaseService<SysAclModuleMapper, SysAclModule> implements ISysAclModuleService {

    @Autowired
    private SysAclMapper sysAclMapper;

    public void saveAclMoudule(AclModuleParam param) {

        BeanValidator.check(param);

        checkExist(param);

        SysAclModule aclModule = new SysAclModule();
        BeanUtils.copyProperties(param, aclModule);
        aclModule.setOperateIp("127.0.0.1");
        aclModule.setOperator("sys");
        aclModule.setOperateTime(new Date());
        save(aclModule);
    }

    private void checkExist(AclModuleParam param) {
        if (count(new QueryWrapper<SysAclModule>().lambda()
                .eq(SysAclModule::getName, param.getName())
                .eq(param.getParentId() != null, SysAclModule::getParentId, param.getParentId())
                .eq(param.getId() != null, SysAclModule::getId, param.getId())) > 0) {
            throw new BusinessException("同一层级下存在相同名称的权限模块");
        }
    }

    /**
     * 模块树获取...
     *
     * @return
     */
    public List<SysAclModuleExt> aclModuleTree() {
        List<SysAclModuleExt> aclModuleExts = list().stream().map(it -> {
            SysAclModuleExt target = new SysAclModuleExt();
            BeanUtils.copyProperties(it, target);
            return target;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(aclModuleExts)) {
            return Lists.newArrayList();
        }

        Multimap<String, SysAclModuleExt> levelAclModuleMap = ArrayListMultimap.create();
        List<SysAclModuleExt> rootList = Lists.newArrayList();

        aclModuleExts.forEach(it -> {
            levelAclModuleMap.put(it.getLevel(), it);
            if (ROOT.equals(it.getLevel())) {
                rootList.add(it);
            }
        });
        Collections.sort(rootList, Comparator.comparingInt(SysAclModule::getSeq));
        transformAclModuleTree(rootList, ROOT, levelAclModuleMap);
        return rootList;
    }

    private void transformAclModuleTree(List<SysAclModuleExt> rootList, String parentLevel, Multimap<String, SysAclModuleExt> levelAclModuleMap) {
        rootList.forEach(it -> {
            String nextLevel = parentLevel + SEPARATOR + it.getId();
            List<SysAclModuleExt> tempList = (List<SysAclModuleExt>) levelAclModuleMap.get(nextLevel);
            if (CollectionUtils.isNotEmpty(tempList)) {
                Collections.sort(tempList, Comparator.comparingInt(SysAclModule::getSeq));
                it.setAclModuleList(tempList);
                transformAclModuleTree(tempList, nextLevel, levelAclModuleMap);
            }
        });
    }

    public void delAclModule(int id) {
        SysAclModule aclModule = getById(id);
        Preconditions.checkNotNull(aclModule, "待删除的权限模块不存在，无法删除!");
        if (count(new QueryWrapper<SysAclModule>().lambda()
                .eq(SysAclModule::getParentId, id)) > 0) {
            throw new BusinessException("当前模块下面有子模块，无法删除!");
        }

        if (sysAclMapper.selectCount(new QueryWrapper<SysAcl>().lambda()
                .eq(SysAcl::getAclModuleId, id)) > 0) {
            throw new BusinessException("当前模块下面挂载有权限，无法删除!");
        }

        removeById(id);
    }

    public void updateAclModule(AclModuleParam param) {
        BeanValidator.check(param);

        SysAclModule before = getById(param.getId());
        Preconditions.checkNotNull(before, "待更新的权限模块不存在!");

        checkExist(param);

        SysAclModule after = new SysAclModule();
        BeanUtils.copyProperties(param, after);

        SysAclModule parent = getById(param.getParentId());
        after.setLevel(LevelUtil.calculateLevel(parent != null ? parent.getLevel() : null, parent.getId()));
        after.setOperator("sys");
        after.setOperateIp("127.0.0.1");

        updateWithChild(before, after);
    }

    @Transactional
    public void updateWithChild(SysAclModule before, SysAclModule after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {
            List<SysAclModule> aclModuleList = list(new QueryWrapper<SysAclModule>().lambda()
                    .like(SysAclModule::getLevel, oldLevelPrefix + ".%"));
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

}