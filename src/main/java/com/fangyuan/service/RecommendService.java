package com.fangyuan.service;

import com.fangyuan.dao.RoomDAO;
import com.fangyuan.entity.HostHolder;
import com.fangyuan.entity.Room;
import com.fangyuan.entity.User;
import com.fangyuan.exception.RecommendException;
import com.fangyuan.exception.RoomException;
import com.fangyuan.util.Similarity;
import com.fangyuan.vo.FangYuanItem;
import com.fangyuan.vo.FangYuanListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendService {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    Similarity similarity;
    @Autowired
    RoomDAO roomDAO;
    public FangYuanListVO recommendRoom() {
        // 初始化数据
        similarity.init();
        List<Integer> ids = similarity.getRecommend(getUserCharacter(), 10);
        FangYuanListVO vo = new FangYuanListVO();
        List<FangYuanItem> items = ids.stream().map(id -> {
            List<Room> rooms = roomDAO.query4Detail(id);
            if (CollectionUtils.isEmpty(rooms)){
                return null;
            }
            Room room = rooms.get(0);
            return convertRoomToItem(room);
        }).collect(Collectors.toList());
        vo.setTotalNum(items.size());
        vo.setRoomItems(items);
        return vo;
    }

    private List<Integer> getUserCharacter(){
        User user = hostHolder.getUser();
        // district, price， area, layout,
        List<Integer> userChar = new ArrayList<>(4);
        Room room = roomDAO.query4Detail(1243).get(0);
        if (room == null){
            return null;
        }
        userChar.add(room.getDistrict());
        userChar.add(room.getPrice());
        userChar.add(room.getArea());
        userChar.add(room.getLayout());
        return userChar;
    }

    private FangYuanItem convertRoomToItem(Room room){
        FangYuanItem item = new FangYuanItem();
        item.setRoomId(room.getId());
        item.setTitle(room.getTitle());
        item.setDistrict(room.getDistrict());
        item.setDistrictCN(room.getDistrictCN());
        item.setArea(room.getArea());
        item.setPrice(room.getPrice());
        item.setLayout(room.getLayoutCN());
        item.setOrient(room.getOrient());
        String tag = room.getTags();
        String[] tags = tag.split("\\|");
        List<String> t = new ArrayList<>();
        for (String str : tags){
            t.add(str);
        }
        item.setTags(t);
        item.setRoomFloor(room.getRoomFloor());
        return item;
    }
}
