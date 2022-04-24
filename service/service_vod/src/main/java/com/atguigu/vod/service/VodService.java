package com.atguigu.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadAliyunFile(MultipartFile file);

    void removeMoreAliyunVideos(List<String> videoIdList);
}
