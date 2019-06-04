package com.fangyuan.controller;

import com.fangyuan.exception.RoomException;
import com.fangyuan.result.Result;
import com.fangyuan.service.RecommendService;
import com.fangyuan.service.RoomService;
import com.fangyuan.so.SearchSO;
import com.fangyuan.vo.BrowserHistoryVO;
import com.fangyuan.vo.FangYuanItem;
import com.fangyuan.vo.FangYuanListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomService roomService;
    @Autowired
    RecommendService recommendService;

    @ResponseBody
    @RequestMapping(path = {"/search"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public Result<FangYuanListVO> search(@RequestParam(value = "district", required = false) Integer district,
                                         @RequestParam(value = "districtList[]", required = false) List<Integer> districtList,
                                         @RequestParam(value = "priceLow", required = false) Integer priceLow,
                                         @RequestParam(value = "priceHigh", required = false) Integer priceHigh,
                                         @RequestParam(value = "areaLow", required = false) Integer areaLow,
                                         @RequestParam(value = "areaHigh", required = false) Integer areaHigh,
                                         @RequestParam(value = "layout", required = false) Integer layout) {
        SearchSO so = new SearchSO();
        so.setPriceHigh(priceHigh);
        so.setPriceLow(priceLow);
        so.setLayout(layout);
        so.setAreaHigh(areaHigh);
        so.setAreaLow(areaLow);
        so.setDistrictList(districtList);
        so.setDistrict(district);
        log.info("[RoomController] search start, param is {}.", so);
        return Result.success(roomService.searchRoom(so));
    }

    @ResponseBody
    @RequestMapping(path = {"/recommend"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public Result<FangYuanListVO> recommend() {
        log.info("[RoomController] recommend start.");
        return Result.success(recommendService.recommendRoom());
    }

    @ResponseBody
    @RequestMapping(path = {"/detail"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public Result<FangYuanItem> detail(@RequestParam(value="roomId") Integer roomId) throws RoomException {
        log.info("[RoomController] detail start.");
        return Result.success(roomService.getRoomDetail(roomId));
    }

    @ResponseBody
    @RequestMapping(path = {"/browser_history"}, method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
    public Result<BrowserHistoryVO> getBrowserHistory() {
        log.info("[RoomController] detail start.");
        return Result.success(roomService.getBrowserHistory());
    }
}
