package com.hhweb.test.api;

import com.hhweb.bean.BeanA;
import com.hhweb.hessian.annotation.HessianClient;

@HessianClient( serviceUrl= "${service.url}/testService")
public interface TestService {

    public BeanA test(BeanA name);

}
