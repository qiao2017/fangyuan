package com.fangyuan.so;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginSO {
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @NotEmpty(message = "密码不能为空")
    private String password;
    private Boolean rememberMe = false;
}
