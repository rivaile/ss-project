package com.rainbow.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rainbow.dao.mapper.SysUserMapper;
import com.rainbow.domain.PageQuery;
import com.rainbow.domain.SysUser;
import com.rainbow.service.ISysUserService;
import com.rainbow.vo.Response;
import com.rainbow.vo.SysUserReq;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ISysUserService sysUserService;

    @ApiOperation(value = "添加用户")
    @PostMapping
    public Response<String> addSysUser(@Valid @RequestBody SysUserReq req) {
        return sysUserService.addSysUser(req);
    }

    @ApiOperation(value = "修改用户")
    @PutMapping
    public Response updateSysUser(@RequestBody SysUserReq sysUserReq) {
        return sysUserService.updateUser(sysUserReq);
    }

    @GetMapping
    public Response<IPage<SysUser>> getUserList(@PageableDefault(page = 2, size = 10, sort = {"name,desc", "pass,ase"}
            , direction = Sort.Direction.ASC) Pageable pageable,
                                                SysUserReq req) {


        Page page = new Page();

//        pageable.getSort().forEach(it -> {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setColumn(it.getProperty());
//            orderItem.setAsc(it.getDirection()== Sort.Direction.ASC)
//        });
//        orderItem.setColumn(pageable.getSort().get)
//        page.setOrders(orderItem);

        return sysUserService.getSysUser(page, req);
    }
}
