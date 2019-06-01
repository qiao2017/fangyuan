package com.fangyuan.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Browser {
    private Integer browserId;
    private Integer roomId;
    private Date lastBrowserTime;
    private Integer times;
    private Integer userId;
}
