package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.atguigu.baseservice.exceptionhandler.GuliException;
import com.atguigu.commonutils.Result;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVodUtils;
import com.atguigu.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;


    //上传视频到阿里云
    @PostMapping("uploadAliyunVideo")
    public Result uploadAliyunVideo(MultipartFile file) {
        System.out.println("1");
        String videoId = vodService.uploadAliyunFile(file);
        System.out.println("2");
        return Result.success().data("videoId", videoId);
    }

    //根据视频id删除视频
    @DeleteMapping("deleteAliyunVideById/{videoId}")
    public Result deleteAliyunVideById(@PathVariable String videoSourceId) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoSourceId);
            client.getAcsResponse(request);
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    //根据视频id删除视频
    @DeleteMapping("deleteAliyunVideBatch")
    public Result deleteAliyunVideBatch(@RequestParam("videoList") List<String> videoIdList) {
        vodService.removeMoreAliyunVideos(videoIdList);
        return Result.success();
    }

    //根据视屏id获取视频凭证
    @GetMapping("getPlayAuth/{videoId}")
    public Result getPlayAuth(@PathVariable String videoId) {
        try {
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET);
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return Result.success().data("playAuth", playAuth);
        } catch (Exception e) {
            throw new GuliException(20001, "获取凭证失败！");
        }
    }
}
