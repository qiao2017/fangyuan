package com.fangyuan.service;

import com.fangyuan.dao.RoomDAO;
import com.fangyuan.entity.HostHolder;
import com.fangyuan.entity.Room;
import com.fangyuan.entity.User;
import com.fangyuan.util.Similarity;
import com.fangyuan.vo.BrowserHistoryItem;
import com.fangyuan.vo.BrowserHistoryVO;
import com.fangyuan.vo.FangYuanItem;
import com.fangyuan.vo.FangYuanListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendService {
    @Autowired
    HostHolder hostHolder;
    @Autowired
    Similarity similarity;
    @Autowired
    RoomService roomService;
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

    private List<Double> getUserCharacter(){
        User user = hostHolder.getUser();
        BrowserHistoryVO browserHistoryVO= roomService.getBrowserHistory();
        List<BrowserHistoryItem> items = browserHistoryVO.getHistoryItems();
        // district, price， area, layout,
        List<Double> userChar = new ArrayList<>(4);
        if(CollectionUtils.isEmpty(items)){
            Room room = roomDAO.query4Detail(1243).get(0);
            if (room == null){
                return null;
            }
            userChar.add(Double.valueOf(room.getDistrict()));
            userChar.add(Double.valueOf(room.getPrice()));
            userChar.add(Double.valueOf(room.getArea()));
            userChar.add(Double.valueOf(room.getLayout()));
        }else{
            Double district = 0.0, price = 0.0, area = 0.0, layout = 0.0;
            int totalTimes = 0;
            for (BrowserHistoryItem item : items) {
                totalTimes += item.getTimes();
                district += item.getTimes() * item.getDistrict();
                price += item.getTimes() * item.getPrice();
                area += item.getTimes() * item.getArea();
                layout += item.getTimes() * item.getLay();
            }
            userChar.add(district * 1.0 / totalTimes);
            userChar.add(price * 1.0 / totalTimes);
            userChar.add(area * 1.0 / totalTimes);
            userChar.add(layout * 1.0 / totalTimes);
        }
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
