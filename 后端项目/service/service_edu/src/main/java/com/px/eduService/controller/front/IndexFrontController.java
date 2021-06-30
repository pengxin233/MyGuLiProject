package com.px.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.px.commonutils.R;
import com.px.eduService.entity.EduCourse;
import com.px.eduService.entity.EduTeacher;
import com.px.eduService.service.EduCourseService;
import com.px.eduService.service.EduTeacherService;
import com.px.eduService.service.index.IndexFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("eduService/indexFront")
public class IndexFrontController {
    @Autowired
    IndexFrontService indexFrontService;

    //查询前8个热门课程，前4个热门名师
    @GetMapping("index")
    public R index(){
        //查询前八个热门课程
        List<EduCourse> courseList = indexFrontService.getHotCourse();

        //查询前四个讲师
        List<EduTeacher> teacherList = indexFrontService.getHotTeacher();

        return R.ok().data("courseList",courseList).data("teacherList",teacherList);


    }
}
