package com.px.eduService.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.px.eduService.entity.EduSubject;
import com.px.eduService.entity.excel.SubjectData;
import com.px.eduService.mapper.EduSubjectMapper;
import com.px.serviceBase.execptionHandler.GuLiException;


public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {
    //spring无法管理SubjectExcelListener,需要自己手动new，所有无法使用spring的功能
    //为了使用service和mapper管理的数据库功能，需要手动吧service注入进来
    private EduSubjectMapper mapper;

    public SubjectExcelListener(EduSubjectMapper mapper) {
        this.mapper = mapper;
    }

    public EduSubjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(EduSubjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null){
            throw new GuLiException(20001,"文件数据为空");
        }

        //一行行读取，每次两个值，第一个为一级分类，第二个为二级分类
        EduSubject existOneSubject = this.existOneSubject(mapper, subjectData.getOneSubject());
        if (existOneSubject == null){//代表数据库中不存在该1级分类
            existOneSubject = new EduSubject();
            existOneSubject.setTitle(subjectData.getOneSubject());
            existOneSubject.setParentId("0");
            mapper.insert(existOneSubject);
        }

        //得到pid,如果existOneSubject == null 经过添加操作后，里面会生成id，如果!=null 则里面一定存在id
        String pid = existOneSubject.getId();
        EduSubject existTwoSubject = this.existTwoSubject(mapper, subjectData.getTwoSubject(),pid);
        if (existTwoSubject == null){//代表数据库中不存在该2级分类
            existTwoSubject = new EduSubject();
            existTwoSubject.setTitle(subjectData.getTwoSubject());
            existTwoSubject.setParentId(pid);
            mapper.insert(existTwoSubject);

        }
    }

    //判断一级分类不能重复
    public EduSubject existOneSubject(EduSubjectMapper mapper,String name){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",0);
        EduSubject one = mapper.selectOne(wrapper);
        return one;
    }

    //二级分类不能重复
    public EduSubject existTwoSubject(EduSubjectMapper mapper,String name,String pid){
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id",pid);
        EduSubject two = mapper.selectOne(wrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
