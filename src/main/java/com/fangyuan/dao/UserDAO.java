package com.fangyuan.dao;

import com.fangyuan.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDAO {
    String TABLE_NAME = " user ";
    String INSERT_FIELDS = " username, password, salt, create_date, nick_name";
    String SELECT_FIELDS = " user_id, " + INSERT_FIELDS;
    String SAFE_SELECT_FIELDS = " user_id, username, create_date, nick_name";

    @Insert({"insert into", TABLE_NAME, " ( ", INSERT_FIELDS, ") values("
            + "#{username}, #{password}, #{salt}, #{createDate}, #{nickName})"})
    int addUser(User user);

    /**
     * 不会泄露密码等信息
     * @param userId
     * @return
     */
    @Select({"select ", SELECT_FIELDS," from ",
            TABLE_NAME, " where user_id = #{userId}"})
    User selectById(int userId);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where username=#{username}"})
    User selectByName(String username);

    @Update({"update ", TABLE_NAME, " set password=#{password} where user_id=#{userId}"})
    void updatePassword(User user);

    @Select({"select count(*) from user"})
    int getUserNum();
    @Select({"select user_id from user order by user_id desc limit 0, 1"})
    int getMaxUserId();
    @Select({"select user_id from user order by user_id asc limit 0, 1"})
    int getMinUserId();
}
