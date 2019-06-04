package com.fangyuan.controller;

import com.fangyuan.result.Result;
import com.fangyuan.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/home")
@Slf4j
public class IndexController {
    @Autowired
    RoomService roomService;

    @RequestMapping(path = {"/homepage"},
            method = {RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS},
            produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result homepage(HttpServletRequest request) {
        String ip = getIpAddress(request);
        log.info("[IndexController] homepage start, ip addr is {}", ip);
        return Result.success(roomService.getHomeData());
    }

     private String getIpAddress(HttpServletRequest request) {
         String ip = request.getHeader("x-forwarded-for");
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getHeader("Proxy-Client-IP");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getHeader("WL-Proxy-Client-IP");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getHeader("HTTP_CLIENT_IP");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getHeader("HTTP_X_FORWARDED_FOR");
         }
         if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
             ip = request.getRemoteAddr();
         }
         return ip;
     }
}
