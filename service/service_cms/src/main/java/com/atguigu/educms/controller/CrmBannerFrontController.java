package com.atguigu.educms.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/educms/bannerfront")
public class CrmBannerFrontController {
    @Autowired
    private CrmBannerService bannerService;

    @GetMapping("getHotBanners")
    public Result getHotBanners() {
        List<CrmBanner> banners = bannerService.getHotBanners();
        return Result.success().data("banners", banners);
    }
}
