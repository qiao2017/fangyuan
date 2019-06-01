package com.fangyuan.entity;

import lombok.Data;

@Data
public class Room {
    private Integer id;
    private Integer district;
    private String districtCN;
    private Integer area;
    private String orient;
    private Integer layout;
    private String layoutCN;
    private String tags;
    private String title;
    private String roomFloor;
    private Integer price;
}
