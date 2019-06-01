package com.fangyuan.controller;

import com.fangyuan.result.CodeMsg;
import com.fangyuan.result.Result;
import com.fangyuan.service.UserService;
import com.fangyuan.so.LoginSO;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    UserService userService;

    @RequestMapping(path = {"/do_login"},
            method = {RequestMethod.GET, RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result doLogin(@Valid @RequestBody LoginSO so,
                          HttpServletResponse response) {
        log.info("[LoginController] doLogin start, param is {}", so);
        String token = userService.login(so);
        if(token != null){
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            if (so.getRememberMe()) {
                cookie.setMaxAge(3600*24*5);
            }
            response.addCookie(cookie);
            return Result.success();
        }else {
            return Result.failure(CodeMsg.PASSWORD_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(path = {"/do_logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Result logout(@CookieValue("token") String ticket) {
        log.info("[LoginController] logout start");
        userService.logout(ticket);
        return Result.success();
    }
}
