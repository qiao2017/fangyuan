package com.fangyuan.vo;

import lombok.Data;

import java.util.List;

@Data
public class BrowserHistoryVO {
    private int totalNum;
    List<BrowserHistoryItem> historyItems;
}
