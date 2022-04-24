package com.atguigu.educms.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/educms/banneradmin")
public class CrmBannerAdminController {
    @Autowired
    private CrmBannerService crmBannerService;

    //分页查询
    @GetMapping("pageBanner/{current}/{size}")
    public Result pageBanner(@PathVariable long current, @PathVariable long size) {
        Page<CrmBanner> crmBannerPage = new Page<>(current, size);
        crmBannerService.page(crmBannerPage, null);
        long total = crmBannerPage.getTotal();
        return Result.success().data("total", total).data("items", crmBannerPage.getRecords());
    }

    @PostMapping("addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.save(crmBanner);
        return Result.success();
    }

    @PutMapping("updateBanner")
    public Result updateBanner(@RequestBody CrmBanner crmBanner) {
        crmBannerService.updateById(crmBanner);
        return Result.success();
    }

    @DeleteMapping("removeBanner/{id}")
    public Result removeBanner(@PathVariable String id) {
        crmBannerService.removeById(id);
        return Result.success();
    }

    @GetMapping("getBannerById/{id}")
    public Result getBannerById(@PathVariable String id) {
        crmBannerService.getById(id);
        return Result.success();
    }
}
