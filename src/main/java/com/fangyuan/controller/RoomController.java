package com.fangyuan.controller;

import com.fangyuan.exception.RoomException;
import com.fangyuan.result.Result;
import com.fangyuan.service.RoomService;
import com.fangyuan.so.SearchSO;
import com.fangyuan.vo.BrowserHistoryVO;
import com.fangyuan.vo.FangYuanItem;
import com.fangyuan.vo.FangYuanListVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("/room")
public class RoomController {
    @Autowired
    RoomService roomService;

    @ResponseBody
    @RequestMapping(path = {"/search"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Result<FangYuanListVO> search(@Valid @RequestBody SearchSO so) {
        log.info("[RoomController] search start, param is {}.", so);
        return Result.success(roomService.searchRoom(so));
    }

    @ResponseBody
    @RequestMapping(path = {"/recommend"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Result<FangYuanListVO> recommend() {
        log.info("[RoomController] recommend start.");
        return Result.success(roomService.getHomeData());
    }

    @ResponseBody
    @RequestMapping(path = {"/detail"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Result<FangYuanItem> detail(@RequestParam(value="roomId") Integer roomId) throws RoomException {
        log.info("[RoomController] detail start.");
        return Result.success(roomService.getRoomDetail(roomId));
    } ;

    @ResponseBody
    @RequestMapping(path = {"/browser_history"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Result<BrowserHistoryVO> getBrowserHistory() {
        log.info("[RoomController] detail start.");
        return Result.success(roomService.getBrowserHistory());
    }
}
