package com.px.eduService.service;

import com.px.eduService.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.eduService.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author px
 * @since 2021-03-11
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程分类
    void saveSubject(MultipartFile file);

    List<OneSubject> getAllOneTwoSubject();
}
