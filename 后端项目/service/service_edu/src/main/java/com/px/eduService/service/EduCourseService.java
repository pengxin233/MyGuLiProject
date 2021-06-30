package com.px.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.commonutils.R;
import com.px.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.eduService.entity.frontVo.CourseFrontVo;
import com.px.eduService.entity.frontVo.CourseWebVo;
import com.px.eduService.entity.vo.CourseInfoVo;
import com.px.eduService.entity.vo.CoursePublishVo;
import com.px.eduService.entity.vo.CourseQuery;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author px
 * @since 2021-03-13
 */
public interface EduCourseService extends IService<EduCourse> {

    void saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String id);

    R pageCourseCondition(long current, long limit, CourseQuery courseQuery);

    void deleteCourse(String id);

    //前台带条件分页查询
    Map<String, Object> getCourseFrontList(Page<EduCourse> eduCoursePage, CourseFrontVo courseFrontVo);

    //通过课程id查询课程信息
    CourseWebVo getCourseInfo(String courseId);
}
