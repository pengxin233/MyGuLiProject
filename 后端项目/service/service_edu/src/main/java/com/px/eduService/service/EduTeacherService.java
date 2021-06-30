package com.px.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.commonutils.R;
import com.px.eduService.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.eduService.entity.vo.TeacherQuery;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author px
 * @since 2021-01-12
 */
public interface EduTeacherService extends IService<EduTeacher> {
    public R pageTeacherCondition(long current,long limit, TeacherQuery teacherQuery);

    //首页讲师的分页查询
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
