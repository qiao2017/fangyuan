package com.fangyuan.vo;

import lombok.Data;

import java.util.List;

@Data
public class FangYuanItem {
    private Integer roomId;
    private String title;
    private Integer district;
    private String districtCN;
    private Integer area;
    private Integer price;
    private String layout;
    private String orient;
    private List<String> tags;
    private String roomFloor;
    private Integer lay;
}
