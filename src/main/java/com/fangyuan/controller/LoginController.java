package com.fangyuan.controller;

import com.fangyuan.result.Result;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @RequestMapping("/to_login")
    public String toLogin() throws Exception {
        throw new Exception("");
    }
    
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin() {
    	return Result.success();
    }
}
