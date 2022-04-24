package com.atguigu.statistics.service.impl;

import com.atguigu.statistics.client.UcenterClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.statistics.entity.StatisticsDaily;
import com.atguigu.statistics.service.StatisticsDailyService;
import com.atguigu.statistics.mapper.StatisticsDailyMapper;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author youyangxiu
 * @description 针对表【statistics_daily(网站统计日数据)】的数据库操作Service实现
 * @createDate 2022-04-20 14:52:37
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily>
        implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @Override
    public void registerCount(String date) {
        //添加之前删除表相同日期的数据
        QueryWrapper<StatisticsDaily> statisticsDailyQueryWrapper = new QueryWrapper<>();
        statisticsDailyQueryWrapper.eq("date_calculated", date);
        if (this.count(statisticsDailyQueryWrapper) > 0) {
            this.remove(statisticsDailyQueryWrapper);
        }
        long registerNum = ucenterClient.countRegister(date);
        long courseNum = RandomUtils.nextLong(100, 200);
        long loginNum = RandomUtils.nextLong(100, 200);
        long videoViewNum = RandomUtils.nextLong(100, 200);

        StatisticsDaily statisticsDaily = new StatisticsDaily();

        statisticsDaily.setRegisterNum((int) registerNum);
        statisticsDaily.setCourseNum((int) courseNum);
        statisticsDaily.setLoginNum((int) loginNum);
        statisticsDaily.setVideoViewNum((int) videoViewNum);
        statisticsDaily.setDateCalculated(date);

        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> showData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> statisticsDailyQueryWrapper = new QueryWrapper<>();
        statisticsDailyQueryWrapper.between("date_calculated", begin, end).select("date_calculated", type);
        List<StatisticsDaily> list = baseMapper.selectList(statisticsDailyQueryWrapper);
        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        for (StatisticsDaily item : list) {
            dateList.add(item.getDateCalculated());
            switch (type) {
                case "login_num":
                    numList.add(item.getLoginNum());
                    break;
                case "register_num":
                    numList.add(item.getRegisterNum());
                    break;
                case "video_view_num":
                    numList.add(item.getVideoViewNum());
                    break;
                case "course_num":
                    numList.add(item.getCourseNum());
                    break;
                default:
                    break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("dateList", dateList);
        map.put("numList", numList);
        return map;
    }
}




