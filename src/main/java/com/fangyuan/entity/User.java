package com.fangyuan.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer userId;
    private String username;
    private String password;
    private String salt;
    private Date createDate;
    private String headUrl;
    private String nickName;
}
