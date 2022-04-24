package com.atguigu.statistics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //查询某一天的注册人数，用于统计分析模块远程调用
    @GetMapping("/educenter/member/countRegister/{date}")
    public long countRegister(@PathVariable("date") String date);
}
