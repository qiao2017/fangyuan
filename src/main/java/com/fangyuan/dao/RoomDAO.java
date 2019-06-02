package com.fangyuan.dao;

import com.fangyuan.entity.Room;
import com.fangyuan.so.SearchSO;
import com.fangyuan.vo.SimilarityVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoomDAO {
    String TABLE_NAME = " room ";
    String INSERT_FIELDS = " district, districtCN, area, orient, layout, layoutCN, tags, title, room_floor, price";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    List<Room> query4Search(@Param("so") SearchSO so);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME,
            " where id = #{id}"})
    List<Room> query4Detail(@Param("id") int id);

    List<Room> batchRooms4Browser(@Param("ids") List<Integer> ids);

    List<SimilarityVO> batchQueryAll4Similarity();
}
