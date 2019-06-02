package com.hhweb.service.api.impl;

import com.hhweb.hessian.annotation.HessianService;
import com.hhweb.service.api.TestService;

@HessianService( path = "/testService", description = "描述接口")
public class TestServiceImpl implements TestService {

    @Override
    public String test(String name) {
        return "你好" + name;
    }
}
