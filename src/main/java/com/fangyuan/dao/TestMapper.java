package com.fangyuan.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
//    @Select("select 1 from test")
    int test();
}
