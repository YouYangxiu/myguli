package com.atguigu.educms.service;

import com.atguigu.educms.entity.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author youyangxiu
* @description 针对表【crm_banner(首页banner表)】的数据库操作Service
* @createDate 2022-04-07 10:50:17
*/
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getHotBanners();
}
