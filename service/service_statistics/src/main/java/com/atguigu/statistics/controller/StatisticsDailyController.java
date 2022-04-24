package com.atguigu.statistics.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RequestMapping("/staservice/statistics")
@RestController
public class StatisticsDailyController {
    //生成统计数据插入到数据表
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //统计某一天的注册人数
    @PostMapping("registerCount/{date}")
    public Result registerCount(@PathVariable String date) {
        statisticsDailyService.registerCount(date);
        return Result.success();
    }

    //图标显示，返回两部分数据
    @GetMapping("showData/{type}/{begin}/{end}")
    public Result showData(@PathVariable String type, @PathVariable String begin, @PathVariable String end) {
        Map<String, Object> resultMap = statisticsDailyService.showData(type, begin, end);
        return Result.success().data("resultMap", resultMap);
    }
}
