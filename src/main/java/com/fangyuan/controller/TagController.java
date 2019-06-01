package com.fangyuan.controller;

import com.fangyuan.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/tag")
public class TagController {
    @ResponseBody
    @RequestMapping(path = {"/district"}, method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Map> logout() {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "西湖");
        map.put(2, "下城");
        map.put(3, "江干");
        map.put(4, "拱墅");
        map.put(5, "上城");
        map.put(6, "滨江");
        map.put(7, "余杭");
        map.put(8, "萧山");
        map.put(9, "富阳");
        map.put(10, "临安");
        map.put(11, "大江东");
        map.put(12, "钱塘新区");
        return Result.success(map);
    }
}
