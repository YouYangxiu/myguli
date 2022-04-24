package com.atguigu.eduservice.nacos;

import com.atguigu.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//@Component
@FeignClient("com.atguigu.msmservice.service-vod")
public interface VodClient {
    //根据视频id删除视频
    @DeleteMapping("/eduvod/video/deleteAliyunVideById/{videoId}")
    public Result deleteAliyunVideById(@PathVariable String videoSourceId);

    //根据视频id删除多个视频
    @DeleteMapping("/eduvod/video/deleteAliyunVideBatch")
    public Result deleteAliyunVideBatch(@RequestParam("videoList") List<String> videoIdList);
}
