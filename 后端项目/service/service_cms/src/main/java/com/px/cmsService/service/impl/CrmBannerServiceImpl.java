package com.px.cmsService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.px.cmsService.entity.CrmBanner;
import com.px.cmsService.mapper.CrmBannerMapper;
import com.px.cmsService.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.commonutils.R;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author px
 * @since 2021-05-16
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    //Redis注解
    @Cacheable(key = "'selectIndexBanner'",value = "banner")
    public List<CrmBanner> selectAllBanner() {
        //根据id降序，查询到前两条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 2");
        List<CrmBanner> crmBanners = baseMapper.selectList(wrapper);
        return crmBanners;
    }
}
