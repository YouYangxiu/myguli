package com.atguigu.eduservice.nacos;

import com.atguigu.commonutils.Result;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VodClientImpl implements VodClient{
    @Override
    public Result deleteAliyunVideById(String videoSourceId) {
        return Result.failure().message("删除视频出错了！！");
    }

    @Override
    public Result deleteAliyunVideBatch(List<String> videoIdList) {
        return Result.failure().message("删除多个视频出错了！！");
    }
}
