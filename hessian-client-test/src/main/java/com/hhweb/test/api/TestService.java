package com.hhweb.test.api;

import com.hhweb.hessian.annotation.HessianClient;

@HessianClient( serviceUrl= "http://localhost:9999/remote/testService")
public interface TestService {

    public String test(String name);
}
