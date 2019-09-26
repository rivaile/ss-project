package com.rainbow.web.controller;

import com.rainbow.domain.SysDeptExt;
import com.rainbow.service.impl.SysDeptService;
import com.rainbow.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: denglin
 * @version: v1.0
 * @Description:
 * @date: 2019-09-26 16:24
 */

@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @GetMapping
    public Response<List<SysDeptExt>> getUserList() {
        return Response.success(sysDeptService.getDeptListTree());
    }
}
