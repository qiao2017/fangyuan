package com.fangyuan.entity;

import lombok.Data;

import java.util.Date;

@Data
public class LoginToken {
    public static Integer  VALID_TOKEN = 0;
    public static Integer INVALID_TOKEN = 1;
    private Integer tokenId;
    private Integer userId;
    private Date expired;
    private Integer status;// 0有效，1无效
    private String token;
}
