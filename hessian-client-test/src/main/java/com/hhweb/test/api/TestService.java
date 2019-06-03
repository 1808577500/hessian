package com.hhweb.test.api;

import com.hhweb.hessian.annotation.HessianClient;

@HessianClient( serviceUrl= "${service.url}/testService")
public interface TestService {

    public String test(String name);
}
