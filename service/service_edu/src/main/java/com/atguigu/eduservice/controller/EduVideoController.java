package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.nacos.VodClient;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/eduservice/eduvideo")
@RestController
@CrossOrigin
public class EduVideoController {

    @Autowired
    private EduVideoService videoService;
    //注入微服务
    @Autowired
    @Qualifier("com.atguigu.eduservice.nacos.VodClient")
    private VodClient vodClient;

    //添加小节
    @PostMapping("/addVideo")
    public Result addVideo(@RequestBody EduVideo video) {
        videoService.save(video);
        return Result.success();
    }

    //根据id查询小节
    @GetMapping("/getVideoById/{videoId}")
    public Result getVideoById(@PathVariable String videoId) {
        EduVideo eduVideo = videoService.getById(videoId);
        return Result.success().data("result", eduVideo);
    }

    //删除小节
    @DeleteMapping("deleteVideoById/{videoId}")
    public Result deleteVideoById(@PathVariable String videoId) {
        EduVideo video = videoService.getById(videoId);
        if (StringUtils.isNotEmpty(video.getVideoSourceId())) {
            vodClient.deleteAliyunVideById(video.getVideoSourceId());
        }
        boolean result = videoService.removeById(videoId);
        return result ? Result.success().message("删除成功！") : Result.failure().message("删除失败！");
    }

    //修改小节
    @PostMapping("updateVideo")
    public Result updateVideo(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return Result.success();
    }
}
