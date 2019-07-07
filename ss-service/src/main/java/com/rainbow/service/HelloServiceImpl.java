package com.rainbow.service;

import com.rainbow.service.impl.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public String greeting(String name) {
        System.err.println(name);
        return null;
    }
}
