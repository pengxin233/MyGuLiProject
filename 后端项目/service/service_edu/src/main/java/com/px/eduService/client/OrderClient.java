package com.px.eduService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-order")
@Component
public interface OrderClient {
    //根据用户id和课程id查询课程的购买信息
    @GetMapping("/eduOrder/order/isBuyCourse/{courseId}/{userId}")
    boolean isBuyCourse(@PathVariable(value = "courseId") String courseId, @PathVariable(value = "userId") String userId);
}
