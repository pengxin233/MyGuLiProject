package com.px.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.commonutils.R;
import com.px.eduService.entity.EduTeacher;
import com.px.eduService.entity.vo.TeacherQuery;
import com.px.eduService.mapper.EduTeacherMapper;
import com.px.eduService.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author px
 * @since 2021-01-12
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public R pageTeacherCondition(long current, long limit, TeacherQuery teacherQuery) {

        Page<EduTeacher> page = new Page<>(current,limit);
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        //StringUtils.isEmpty(name) 判断是否为空或者为空字符串
        //模糊查询名字
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }

        //等于等级
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }

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


//        service.page(page,wrapper);
        baseMapper.selectPage(page,wrapper);

        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageParam) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //根据sort降序排序
        wrapper.orderByDesc("sort");
        baseMapper.selectPage(pageParam, wrapper);

        List<EduTeacher> records = pageParam.getRecords();
        long current = pageParam.getCurrent();//当前第几页
        long pages = pageParam.getPages();//一共多少页
        long size = pageParam.getSize();//当前页个数
        long total = pageParam.getTotal(); //总记录数
        boolean hasNext = pageParam.hasNext();//是否有下一页
        boolean hasPrevious = pageParam.hasPrevious();//是否有上一页

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
}
