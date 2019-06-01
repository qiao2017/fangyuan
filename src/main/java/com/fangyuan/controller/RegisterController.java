package com.fangyuan.controller;

import com.fangyuan.exception.RegisterException;
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
@RequestMapping("/register")
@Slf4j
public class RegisterController {
    @Autowired
    UserService userService;

    @RequestMapping(path = {"/do_register"},
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result doRegister(@Valid @RequestBody LoginSO so,
                          HttpServletResponse response) throws RegisterException {
        log.info("[LoginController] doRegister start, param is {}", so);
        String token = userService.register(so);
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        if (so.getRememberMe()) {
            cookie.setMaxAge(3600*24*5);
        }
        response.addCookie(cookie);
        return Result.success();
    }
}
