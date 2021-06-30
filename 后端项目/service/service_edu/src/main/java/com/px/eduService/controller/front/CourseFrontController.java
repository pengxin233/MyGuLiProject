package com.px.eduService.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.commonutils.CourseWebOrder;
import com.px.commonutils.JwtUtils;
import com.px.commonutils.R;
import com.px.eduService.client.OrderClient;
import com.px.eduService.entity.EduCourse;
import com.px.eduService.entity.chapter.ChapterVo;
import com.px.eduService.entity.frontVo.CourseFrontVo;
import com.px.eduService.entity.frontVo.CourseWebVo;
import com.px.eduService.service.EduChapterService;
import com.px.eduService.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduService/courseFront")
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private OrderClient orderClient;

    //带条件分页查询
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable Integer page, @PathVariable Integer limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){

        Page<EduCourse> eduCoursePage = new Page<>(page,limit);

        Map<String,Object> map = courseService.getCourseFrontList(eduCoursePage,courseFrontVo);
        return R.ok().data(map);
    }

    //2 课程详情
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){

        //根据课程id查询课程信息
        CourseWebVo courseWebVo = courseService.getCourseInfo(courseId);
        //根据课程id得到章节小节
        List<ChapterVo> chapterVideo = eduChapterService.getChapterVideo(courseId);

        //查询课程支付状态
        boolean isBuyCourse = orderClient.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("courseWebVo",courseWebVo).data("chapterVideo",chapterVideo).data("isbuyCourse",isBuyCourse);
    }

    //根据课程id查询课程信息
    @GetMapping("getCourseInfoOrder/{id}")
    public CourseWebOrder getCourseInfoOrder(@PathVariable String id){
        //得到订单需要的课程信息
        CourseWebVo info = courseService.getCourseInfo(id);
        CourseWebOrder order = new CourseWebOrder();

        BeanUtils.copyProperties(info,order);
        return order;
    }

    //支付成功后更新课程的购买数
    @PutMapping("updateBuyCount/{id}")
    public R updateBuyCount(@PathVariable String id){
        //通过id查询得到数据
        EduCourse course = courseService.getById(id);
        course.setBuyCount(course.getBuyCount()+1L);

        //更新
        courseService.updateById(course);
        return R.ok();
    }

    //浏览课程后更新课程的浏览数
    @PutMapping("updateViewCount/{id}")
    public R updateViewCount(@PathVariable String id){
        //通过id查询得到数据
        EduCourse course = courseService.getById(id);
        course.setViewCount(course.getViewCount()+1L);

        //更新
        courseService.updateById(course);
        return R.ok();
    }
}
