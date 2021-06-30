package com.px.eduService.mapper;

import com.px.eduService.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.px.eduService.entity.frontVo.CourseWebVo;
import com.px.eduService.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author px
 * @since 2021-03-13
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //根据课程id查询课程信息
    CoursePublishVo getPublishCourseInfo(String id);

    //根据课程id查询课程基本信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
