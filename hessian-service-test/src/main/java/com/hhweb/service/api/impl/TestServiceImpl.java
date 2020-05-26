package com.hhweb.service.api.impl;

import com.hhweb.bean.BeanA;
import com.hhweb.hessian.annotation.HessianService;
import com.hhweb.service.api.TestService;

@HessianService( path = "/testService", description = "描述接口")
public class TestServiceImpl implements TestService {


    @Override
    public BeanA test(BeanA name) {
        System.out.println(name);
        name.setProB("哈哈哈");
        return name;
    }
}
