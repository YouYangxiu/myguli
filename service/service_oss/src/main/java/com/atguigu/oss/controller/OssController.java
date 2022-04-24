package com.atguigu.oss.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public Result uploadOssFile(MultipartFile file) throws Exception {
        //获取上传的文件
        String url = ossService.uploadAvatarFile(file);
        return Result.success().data("url", url);
    }
}
