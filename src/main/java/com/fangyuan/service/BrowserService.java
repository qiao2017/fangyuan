package com.fangyuan.service;

import com.fangyuan.dao.BrowserDAO;
import com.fangyuan.dao.RoomDAO;
import com.fangyuan.vo.BrowserHistoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BrowserService {
    @Autowired
    BrowserDAO browserDAO;
    @Autowired
    RoomDAO roomDAO;

    public BrowserHistoryVO queryBrowserHistory(){
        BrowserHistoryVO vo = new BrowserHistoryVO();
        return vo;
    }
}
