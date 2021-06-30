package com.px.eduService.controller;


import com.px.commonutils.R;
import com.px.eduService.entity.subject.OneSubject;
import com.px.eduService.service.EduSubjectService;
import com.px.eduService.service.impl.EduSubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author px
 * @since 2021-03-11
 */
@RestController
@CrossOrigin
@RequestMapping("/eduService/subject")
public class EduSubjectController {

    @Autowired
    private EduSubjectService service;

    //添加课程分类
    //获取到上传过来的文件 读取内容
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //上传过来的文件
        service.saveSubject(file);
        return R.ok();
    }

    //得到一级分类和二级分类
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list = service.getAllOneTwoSubject();

        return R.ok().data("list",list);
    }

}

