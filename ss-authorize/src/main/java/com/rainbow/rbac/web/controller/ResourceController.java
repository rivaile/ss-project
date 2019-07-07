package com.rainbow.rbac.web.controller;

import com.rainbow.core.support.SimpleResponse;
import com.rainbow.rbac.domain.Admin;
import com.rainbow.rbac.dto.ResourceInfo;
import com.rainbow.rbac.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

/**
 * @author denglin
 * @version V1.0
 * @Description:
 * @ClassName: ResourceController
 * @date 2018/9/25 17:08
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    /**
     * 获取资源树
     *
     * @param admin
     * @return
     */
    @GetMapping
    public ResourceInfo getTree(@AuthenticationPrincipal Admin admin) {
        return resourceService.getTree(admin.getId());
    }

    /**
     * 获取资源信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResourceInfo getInfo(@PathVariable Long id) {
        return resourceService.getInfo(id);
    }

    /**
     * 创建资源
     *
     * @param info
     * @return
     */
    @PostMapping
    public ResourceInfo create(@RequestBody ResourceInfo info) {
        if (info.getParentId() == null) {
            info.setParentId(0L);
        }
        return resourceService.create(info);
    }

    /**
     * 修改资源
     *
     * @param info
     * @return
     */
    @PutMapping("/{id}")
    public ResourceInfo update(@RequestBody ResourceInfo info) {
        return resourceService.update(info);
    }

    /**
     * 删除
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        resourceService.delete(id);
    }

    /**
     * 资源上移
     *
     * @param id
     */
    @PostMapping("/{id}/up")
    public SimpleResponse moveUp(@PathVariable Long id) {
        return new SimpleResponse(resourceService.move(id, true));
    }

    /**
     * 资源下移
     *
     * @param id
     */
    @PostMapping("/{id}/down")
    public SimpleResponse moveDown(@PathVariable Long id) {
        return new SimpleResponse(resourceService.move(id, false));
    }
}
