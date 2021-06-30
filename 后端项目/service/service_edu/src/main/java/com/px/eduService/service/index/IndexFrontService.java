package com.px.eduService.service.index;

import com.px.eduService.entity.EduCourse;
import com.px.eduService.entity.EduTeacher;

import java.util.List;

public interface IndexFrontService {
    List<EduTeacher> getHotTeacher();

    List<EduCourse> getHotCourse();
}
