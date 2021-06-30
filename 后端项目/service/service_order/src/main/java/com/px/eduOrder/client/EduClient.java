package com.px.eduOrder.client;

import com.px.commonutils.CourseWebOrder;
import com.px.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Component
@FeignClient("service-edu")
public interface EduClient {
    //根据课程id查询课程信息
    @GetMapping("/eduService/courseFront/getCourseInfoOrder/{id}")
    CourseWebOrder getCourseInfoOrder(@PathVariable("id") String id);

    //支付成功后更新课程的购买数
    @PutMapping("/eduService/courseFront/updateBuyCount/{id}")
    R updateBuyCount(@PathVariable("id") String id);
}
