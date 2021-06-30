package com.px.eduService.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.px.commonutils.R;
import com.px.eduService.entity.EduChapter;
import com.px.eduService.entity.EduVideo;
import com.px.eduService.entity.chapter.ChapterVo;
import com.px.eduService.service.EduChapterService;
import com.px.eduService.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author px
 * @since 2021-03-13
 */
@RestController
@CrossOrigin
@RequestMapping("/eduService/chapter")
public class EduChapterController {

    @Autowired
    EduChapterService eduChapterService;

    @Autowired
    EduVideoService eduVideoService;

    //课程大纲列表根据课程id查询查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable(value = "courseId") String courseId){
        List<ChapterVo> list = eduChapterService.getChapterVideo(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    //根据章节id查询
    @GetMapping("findChapterById/{chapterId}")
    public R findChapterById(@PathVariable String chapterId){
        EduChapter chapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",chapter);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    //章节删除
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = eduChapterService.deleteChapter(chapterId);
        return flag ? R.ok() : R.error();
    }
}

