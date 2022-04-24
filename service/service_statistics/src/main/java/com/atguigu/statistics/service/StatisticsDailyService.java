package com.atguigu.statistics.service;

import com.atguigu.statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author youyangxiu
* @description 针对表【statistics_daily(网站统计日数据)】的数据库操作Service
* @createDate 2022-04-20 14:52:37
*/
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void registerCount(String date);

    Map<String, Object> showData(String type, String begin, String end);
}
