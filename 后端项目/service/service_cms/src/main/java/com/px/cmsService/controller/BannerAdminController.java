package com.px.cmsService.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.cmsService.entity.CrmBanner;
import com.px.cmsService.service.CrmBannerService;
import com.px.commonutils.R;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author px
 * @since 2021-05-16
 */
@RestController
@CrossOrigin
@RequestMapping("/cmsService/bannerAdmin")
public class BannerAdminController {
    @Autowired
    CrmBannerService service;
    //分页查询
    @GetMapping("pageBanner/{current}/{limit}")
    public R pageBanner(@PathVariable("current") long current,@PathVariable("limit") long limit){
        Page<CrmBanner> page = new Page<>(current,limit);
        IPage<CrmBanner> page1 = service.page(page, null);
        return R.ok().data("items",page1.getRecords()).data("total",page1.getTotal());
    }

    //获取banner
    @GetMapping("get/{id}")
    public R get(@PathVariable("id") String id){
        CrmBanner byId = service.getById(id);
        return R.ok().data("item",byId);
    }

    //添加banner
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CrmBanner crmBanner){
        service.save(crmBanner);
        return R.ok();
    }

    //修改
    @PutMapping("updateBanner")
    public R updateBanner(@RequestBody CrmBanner crmBanner){
        service.updateById(crmBanner);
        return R.ok();
    }

    //删除
    @DeleteMapping("removeBanner/{id}")
    public R removeBanner(@PathVariable("id") String id){
        service.removeById(id);
        return R.ok();
    }
}

