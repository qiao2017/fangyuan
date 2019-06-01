package com.fangyuan.controller;


import com.fangyuan.dao.TestMapper;
import com.fangyuan.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    TestMapper testMapper;
    @ResponseBody
    @RequestMapping("/11")
    public String test(@RequestBody Test s){
        System.out.println(testMapper.test());
        System.out.println(s);
        return "hello";
    }
}
