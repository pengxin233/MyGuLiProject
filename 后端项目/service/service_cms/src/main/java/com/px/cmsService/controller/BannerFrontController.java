package com.px.cmsService.controller;

import com.px.cmsService.entity.CrmBanner;
import com.px.cmsService.service.CrmBannerService;
import com.px.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("cmsService/bannerFront")
public class BannerFrontController {
    @Autowired
    CrmBannerService service;
    //前台banner显示

    //查询所有banner
    @GetMapping("getAll")
    public R getAll(){
        List<CrmBanner> list = service.selectAllBanner();
        return R.ok().data("list",list);
    }

}
