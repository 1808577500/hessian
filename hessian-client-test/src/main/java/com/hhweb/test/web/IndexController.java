package com.hhweb.test.web;

import com.hhweb.bean.BeanA;
import com.hhweb.test.api.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/")
public class IndexController {

    @Autowired
    private TestService testService;

    @RequestMapping("/")
    public BeanA index( String name){
        BeanA beanA = new BeanA();
        beanA.setProA(name);
        return testService.test(beanA);
    }
}
