package com.fangyuan.dao;

import com.fangyuan.entity.Browser;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BrowserDAO {
    String TABLE_NAME = " browser ";
    String INSERT_FIELDS = " user_id, room_id, last_browser_time, times ";
    String SELECT_FIELDS = "browser_id, " + INSERT_FIELDS;

    @Select({"select count(browser_id) from ", TABLE_NAME, "where user_id = #{userId} and room_id = #{roomId}"})
    int countBrowser(@Param("userId") Integer userId, @Param("roomId") Integer roomId);

    @Update({"update ", TABLE_NAME, " set times = times + 1 where user_id = #{userId} and room_id = #{roomId} "})
    int updateBrowserTimes(@Param("userId") Integer userId, @Param("roomId") Integer roomId);

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS, ")values(",
            "#{browser.userId}, #{browser.roomId},now(), 1)"})
    void insertBrowser(@Param("browser")Browser browser);

    @Select({"select", SELECT_FIELDS, " from ", TABLE_NAME, "where user_id = #{userId}"})
    List<Browser> selectBrowserByUserIdAndRoomId(@Param("userId") Integer userId);
}
