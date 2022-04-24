package com.atguigu.educms.service.impl;

import com.atguigu.educms.entity.CrmBanner;
import com.atguigu.educms.mapper.CrmBannerMapper;
import com.atguigu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author youyangxiu
 * @description 针对表【crm_banner(首页banner表)】的数据库操作Service实现
 * @createDate 2022-04-07 10:50:17
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner>
        implements CrmBannerService {

    @Override
    @Cacheable(key = "'selectIndexList'", value = "banner")
    public List<CrmBanner> getHotBanners() {
        QueryWrapper<CrmBanner> crmBannerQueryWrapper = new QueryWrapper<>();
        crmBannerQueryWrapper.orderByDesc("id").last("limit 2");
        List<CrmBanner> banners = this.list(crmBannerQueryWrapper);
        return banners;
    }
}




