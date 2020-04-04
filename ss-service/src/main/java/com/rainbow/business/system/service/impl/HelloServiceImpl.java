package com.rainbow.business.system.service.impl;

import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String greeting(String name) {
        System.err.println(name);
        return null;
    }
}
