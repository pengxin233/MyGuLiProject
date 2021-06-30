package com.px.eduService.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.commonutils.R;
import com.px.eduService.entity.EduCourse;
import com.px.eduService.entity.EduTeacher;
import com.px.eduService.service.EduCourseService;
import com.px.eduService.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduService/teacherFront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable Integer page,@PathVariable Integer limit){
        Page<EduTeacher> teacherPage = new Page<>(page,limit);

        Map<String,Object> map = teacherService.getTeacherFrontList(teacherPage);

        //返回分页所需要数据
        return R.ok().data(map);
    }

    //讲师详情
    @GetMapping("getTeacherInfo/{id}")
    public R getTeacherInfo(@PathVariable String id){
        //根据id查询讲师信息

        EduTeacher teacher = teacherService.getById(id);

        //查询讲师主讲课程信息
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",id);
        List<EduCourse> courseList = courseService.list(wrapper);

        return R.ok().data("teacher",teacher).data("courseList",courseList);
    }
}
