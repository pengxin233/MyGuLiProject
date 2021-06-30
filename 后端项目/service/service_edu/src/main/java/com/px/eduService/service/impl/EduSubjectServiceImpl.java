package com.px.eduService.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.px.eduService.entity.EduSubject;
import com.px.eduService.entity.excel.SubjectData;
import com.px.eduService.entity.subject.OneSubject;
import com.px.eduService.entity.subject.TwoSubject;
import com.px.eduService.listener.SubjectExcelListener;
import com.px.eduService.mapper.EduSubjectMapper;
import com.px.eduService.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author px
 * @since 2021-03-11
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {


    //因为继承了ServiceImpl，而该类里面已经注入了EduSubjectMapper 为baseMapper
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class,new SubjectExcelListener(baseMapper)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有一级分类 parent_id =0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);


        //查询所有二级分类 parent_id !=0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id",0);
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list用于封装数据
        List<OneSubject> finalList = new ArrayList<>();
        //封装一级数据
        for (EduSubject eduSubject : oneSubjectList) {
            OneSubject oneSubject = new OneSubject();
//            oneSubject.setId(eduSubject.getId());
//            oneSubject.setTitle(eduSubject.getTitle());

            //该工具类的方法可以将一个bean中的属性复制到另一个类
            BeanUtils.copyProperties(eduSubject,oneSubject);

            //遍历二级数据
            for (EduSubject eduSubject2 : twoSubjectList) {
                if (eduSubject2.getParentId().equals(eduSubject.getId())){

                    TwoSubject twoSubject = new TwoSubject();

                    BeanUtils.copyProperties(eduSubject2,twoSubject);

                    oneSubject.getChildren().add(twoSubject);
                }
            }
            finalList.add(oneSubject);
        }

        //封装二级数据



        return finalList;
    }
}
