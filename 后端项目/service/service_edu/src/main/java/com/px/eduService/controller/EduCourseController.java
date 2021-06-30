package com.px.eduService.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.commonutils.R;
import com.px.eduService.entity.EduCourse;
import com.px.eduService.entity.vo.CourseInfoVo;
import com.px.eduService.entity.vo.CoursePublishVo;
import com.px.eduService.entity.vo.CourseQuery;
import com.px.eduService.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/eduService/course")
public class EduCourseController {
    @Autowired
    EduCourseService service;

    //添加课程信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        service.saveCourseInfo(courseInfoVo);
        return R.ok().data("id",courseInfoVo.getId());
    }

    //根据课程id查询
    @GetMapping("getCourseInfoById/{courseId}")
    public R getCourseInfoById(@PathVariable(value = "courseId") String courseId){
        CourseInfoVo courseInfoVo = service.getCourseInfoById(courseId);
        return R.ok().data("courseInfo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        service.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = service.getPublishCourseInfo(id);
        return R.ok().data("info",coursePublishVo);
    }

    //课程的最终发布
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse = new EduCourse();
        //修改
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        service.updateById(eduCourse);
        return R.ok();
    }

    //课程列表带分页和条件查询
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current, @PathVariable long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery){

        R r = service.pageCourseCondition(current,limit,courseQuery);
        return r;
    }

    //删除课程信息
    @DeleteMapping("{id}")
    public R deleteCourse(@PathVariable String id){
//        service.removeById(id);
        service.deleteCourse(id);
        return R.ok();
    }
}

