package com.hhweb.service.api.impl;

import com.hhweb.hessian.annotation.HessianService;
import com.hhweb.service.api.DeomService;
import com.hhweb.service.api.TestService;
import org.springframework.beans.factory.annotation.Autowired;

@HessianService( path = "/testService", description = "描述接口")
public class TestServiceImpl implements TestService {

    @Autowired
    private DeomService deomService;

    @Override
    public String test(String name) {
        deomService.demo();
        return "你好" + name;
    }
}
