package com.fangyuan.so;

import lombok.Data;

import java.util.List;

@Data
public class SearchSO {
    private Integer district;
    private List<Integer> districtList;
    private Integer priceLow;
    private Integer priceHigh;
    private Integer areaLow;
    private Integer areaHigh;
    private Integer layout;
}
