package com.fangyuan.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BrowserHistoryItem extends FangYuanItem {
    private Date lastBrowserTime;
    private Integer times;
}
