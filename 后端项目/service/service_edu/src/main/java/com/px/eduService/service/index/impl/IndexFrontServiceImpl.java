package com.px.eduService.service.index.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.px.eduService.entity.EduCourse;
import com.px.eduService.entity.EduTeacher;
import com.px.eduService.service.EduCourseService;
import com.px.eduService.service.EduTeacherService;
import com.px.eduService.service.index.IndexFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexFrontServiceImpl implements IndexFrontService {
    @Autowired
    EduCourseService courseService;
    @Autowired
    EduTeacherService teacherService;

    @Override
    @Cacheable(value = "index",key = "'HotTeacher'")
    public List<EduTeacher> getHotTeacher() {
        //查询前四个讲师
        QueryWrapper<EduTeacher> wrapper1 = new QueryWrapper<>();
        wrapper1.orderByDesc("id");
        wrapper1.last("limit 4");
        return teacherService.list(wrapper1);
    }

    @Override
    @Cacheable(value = "index",key = "'HotCourse'")
    public List<EduCourse> getHotCourse() {
        //查询前八个热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("view_count");
        wrapper.last("limit 8");

        return courseService.list(wrapper);
    }
}
