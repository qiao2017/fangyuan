package com.fangyuan.vo;

import lombok.Data;

import java.util.List;

@Data
public class FangYuanListVO {
    private int totalNum;
    List<FangYuanItem> roomItems;
}
