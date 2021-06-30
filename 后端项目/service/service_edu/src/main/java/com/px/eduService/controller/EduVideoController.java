package com.px.eduService.controller;


import com.px.commonutils.R;
import com.px.eduService.client.VodClient;
import com.px.eduService.entity.EduVideo;
import com.px.eduService.service.EduVideoService;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author px
 * @since 2021-03-13
 */
@RestController
@CrossOrigin
@RequestMapping("/eduService/video")
public class EduVideoController {

    @Autowired
    EduVideoService eduVideoService;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    //删除小节 并且删除对于的阿里云视频
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        eduVideoService.removeVideoById(id);
        return R.ok();
    }

    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    //根据小节id进行查询
    @GetMapping("findVideoById/{id}")
    public R findVideoById(@PathVariable String id){
        EduVideo video = eduVideoService.getById(id);
        return R.ok().data("video",video);
    }
}

