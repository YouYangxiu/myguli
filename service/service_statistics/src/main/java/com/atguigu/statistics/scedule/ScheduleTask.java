package com.atguigu.statistics.scedule;

import com.atguigu.statistics.service.StatisticsDailyService;
import com.atguigu.statistics.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduleTask {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @Scheduled(cron = "0 0 1 * * ? ")
    //每天凌晨一点，把前一天的数据查询、添加
    public void timedTask() {
        statisticsDailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
