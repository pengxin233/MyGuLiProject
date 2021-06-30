package com.px.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.commonutils.R;
import com.px.eduService.entity.EduCourse;
import com.px.eduService.entity.EduCourseDescription;
import com.px.eduService.entity.EduVideo;
import com.px.eduService.entity.frontVo.CourseFrontVo;
import com.px.eduService.entity.frontVo.CourseWebVo;
import com.px.eduService.entity.vo.CourseInfoVo;
import com.px.eduService.entity.vo.CoursePublishVo;
import com.px.eduService.entity.vo.CourseQuery;
import com.px.eduService.mapper.EduCourseMapper;
import com.px.eduService.service.EduChapterService;
import com.px.eduService.service.EduCourseDescriptionService;
import com.px.eduService.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.eduService.service.EduVideoService;
import com.px.serviceBase.execptionHandler.GuLiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author px
 * @since 2021-03-13
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Autowired
    EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    EduVideoService eduVideoService;
    @Autowired
    EduChapterService eduChapterService;



    @Override
    @Transactional
    //添加课程信息
    public void saveCourseInfo(CourseInfoVo courseInfoVo) {
        //向课程表添加数据
//        System.out.println(courseInfoVo.getId()); null
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
//        System.out.println(eduCourse.getId()); null
        int insert = baseMapper.insert(eduCourse);//insert代表成功影响行数,如果大于1为运行成功，如果为0，失败
        if (insert<=0){
            throw new GuLiException(20001,"添加课程信息失败");
        }

        //抛出异常，查看是否会回滚
//        int i = 10/0; 确实会回滚

//        System.out.println(eduCourse.getId());1370638937721798657

        courseInfoVo.setId(eduCourse.getId());//将id传入courseInfoVo中，方便返回给前端

        //开始传递courseInfoVo的时候没有写入id的值，所以默认为空，但是当baseMapper.insert(eduCourse)
        //成功后，会自动创建一个新的id值，得到之后再赋值给courseInfoVo，
        // BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);判断id不为空则也会进行赋值
        //获取添加后的课程id
        String cid = eduCourse.getId();
        courseInfoVo.setId(cid);

        //向课程简介中加数据
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        eduCourseDescriptionService.save(eduCourseDescription);
    }

    @Override
    @Transactional(readOnly = true)
    public CourseInfoVo getCourseInfoById(String courseId) {
        //查询到课程信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //查询课程简介
        EduCourseDescription description = eduCourseDescriptionService.getById(courseId);

        //将eduCourse封装到courseInfoVo
        CourseInfoVo courseInfoVo = new CourseInfoVo();

        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        courseInfoVo.setDescription(description.getDescription());

        return courseInfoVo;
    }

    //修改
    @Override
    @Transactional
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        EduCourse eduCourse = new EduCourse();
        EduCourseDescription description = new EduCourseDescription();

        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        BeanUtils.copyProperties(courseInfoVo,description);

        baseMapper.updateById(eduCourse);
        eduCourseDescriptionService.updateById(description);
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String id) {
        //调用方法进行查询
        CoursePublishVo info = baseMapper.getPublishCourseInfo(id);
        return info;
    }

    //带分页的条件查询
    @Override
    public R pageCourseCondition(long current, long limit, CourseQuery courseQuery) {
        //分页查询
        Page<EduCourse> page = new Page(current,limit);
        //条件查询
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();

        //标题查询
        if (!StringUtils.isEmpty(title))
            wrapper.like("title",title);

        //查询状态
        if (!StringUtils.isEmpty(status))
            wrapper.eq("status",status);


        //大于开始时间
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }

        //小于结束时间
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        //根据时间排序
        wrapper.orderByDesc("gmt_create");

        IPage<EduCourse> ipage = baseMapper.selectPage(page, wrapper);

        long total = ipage.getTotal();
        List<EduCourse> records = ipage.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    //删除课程
    @Override
    @Transactional
    public void deleteCourse(String id) {
        //先删除小节
        eduVideoService.removeByCourseId(id);
        //删除章节
        eduChapterService.removeByCourseId(id);
        //删除描述
        eduCourseDescriptionService.removeById(id);
        //删除课程
        baseMapper.deleteById(id);
    }

    //条件查询带分页
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam, CourseFrontVo courseFrontVo) {

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值不为空
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){//一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){//二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){//关注度
            wrapper.orderByDesc("buy_count");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){//最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){//价格
            wrapper.orderByDesc("price");
        }

        baseMapper.selectPage(pageParam, wrapper);

        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;


    }

    @Override
    //根据课程id查询课程信息
    public CourseWebVo getCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
