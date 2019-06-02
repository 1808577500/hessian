package com.hhweb.service.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/")
public class IndexController {

    @RequestMapping("/")
    public String index( String name){
        return "service";
    }
}
