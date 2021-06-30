package com.px.eduService.entity.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
//课程信息发布显示的信息
public class CoursePublishVo {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String oneSubject;
    private String twoSubject;
    private String teacherName;
    private BigDecimal price;//只用于显示

}
