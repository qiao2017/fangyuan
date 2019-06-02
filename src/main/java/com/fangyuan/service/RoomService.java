package com.fangyuan.service;

import com.fangyuan.dao.BrowserDAO;
import com.fangyuan.dao.RoomDAO;
import com.fangyuan.entity.Browser;
import com.fangyuan.entity.HostHolder;
import com.fangyuan.entity.Room;
import com.fangyuan.entity.User;
import com.fangyuan.exception.RoomException;
import com.fangyuan.so.SearchSO;
import com.fangyuan.vo.BrowserHistoryItem;
import com.fangyuan.vo.BrowserHistoryVO;
import com.fangyuan.vo.FangYuanItem;
import com.fangyuan.vo.FangYuanListVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RoomService {
    @Autowired
    RoomDAO roomDAO;
    @Autowired
    BrowserDAO browserDAO;
    @Autowired
    HostHolder hostHolder;

    public FangYuanListVO searchRoom(SearchSO so){
        List<Room> rooms = roomDAO.query4Search(so);
        List<FangYuanItem> items = rooms.stream().map(room -> {
            return convertRoomToItem(room);
        }).collect(Collectors.toList());
        FangYuanListVO vo = new FangYuanListVO();
        vo.setRoomItems(items);
        vo.setTotalNum(items.size());
        return vo;
    }
    public FangYuanListVO getHomeData(){
        FangYuanListVO vo = new FangYuanListVO();
        vo.setTotalNum(1);
        List<FangYuanItem> rooms = new ArrayList<>();
        FangYuanItem item = new FangYuanItem();
        item.setArea(10);
        item.setLayout("四室三厅");
        item.setOrient("朝南");
        item.setTitle("标题");
        item.setPrice(10000);
        item.setDistrictCN("西湖区");
        item.setDistrict(1);
        item.setRoomId(1);
        item.setRoomFloor("六楼电梯房");
        item.setTags(Arrays.asList("近地铁", "朝南"));
        rooms.add(item);
        vo.setRoomItems(rooms);
        return vo;
    }

    public FangYuanItem getRoomDetail(Integer roomId) throws RoomException {
        List<Room> rooms = roomDAO.query4Detail(roomId);
        if (CollectionUtils.isEmpty(rooms)){
            throw new RoomException("房源不存在");
        }
        Room room = rooms.get(0);
        // 添加浏览记录
        User user = hostHolder.getUser();
        if (browserDAO.countBrowser(user.getUserId(), room.getId()) > 0){
            browserDAO.updateBrowserTimes(user.getUserId(), room.getId());
            return convertRoomToItem(room);
        }
        Browser browser = new Browser();
        browser.setRoomId(room.getId());
        browser.setUserId(user.getUserId());
        browserDAO.insertBrowser(browser);
        return convertRoomToItem(room);
    }

    public BrowserHistoryVO getBrowserHistory(){
        List<Browser> browsers = browserDAO.selectBrowserByUserIdAndRoomId(hostHolder.getUser().getUserId());
        List<BrowserHistoryItem> items = convertBrowserToItem(browsers);
        BrowserHistoryVO vo = new BrowserHistoryVO();
        vo.setTotalNum(items.size());
        vo.setHistoryItems(items);
        return vo;
    }

    private List<BrowserHistoryItem> convertBrowserToItem(List<Browser> browsers) {
        List<Integer> ids = browsers.stream().map(browser -> {
            return browser.getRoomId();
        }).collect(Collectors.toList());
        List<Room> rooms = roomDAO.batchRooms4Browser(ids);
        Map<Integer, Room> roomMap = rooms.stream().collect(Collectors.toMap(Room::getId, Function.identity()));

        List<BrowserHistoryItem> items = browsers.stream().map(browser -> {
            BrowserHistoryItem item = new BrowserHistoryItem();
            Integer roomId = browser.getRoomId();
            Room room = null;
            if (roomId != null){
                room = roomMap.get(roomId);
            }
            if (room == null){
                return null;
            }

            item.setLastBrowserTime(browser.getLastBrowserTime());
            item.setTimes(browser.getTimes());
            item.setTitle(room.getTitle());
            item.setDistrict(room.getDistrict());
            item.setDistrictCN(room.getDistrictCN());
            item.setArea(room.getArea());
            item.setPrice(room.getPrice());
            item.setLayout(room.getLayoutCN());
            item.setOrient(room.getOrient());
            item.setTags(Arrays.asList(room.getTags()));
            item.setRoomFloor(room.getRoomFloor());
            item.setRoomId(room.getId());
            return item;
        }).collect(Collectors.toList());
        return items;
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
