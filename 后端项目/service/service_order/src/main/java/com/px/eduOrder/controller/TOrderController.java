package com.px.eduOrder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.px.commonutils.JwtUtils;
import com.px.commonutils.R;
import com.px.eduOrder.entity.TOrder;
import com.px.eduOrder.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author px
 * @since 2021-06-23
 */
@RestController
@CrossOrigin
@RequestMapping("/eduOrder/order")
public class TOrderController {
    @Autowired
    private TOrderService service;

    //1.生成订单
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单返回订单号
        String orderNo = service.createOrders(courseId,memberId);
        return R.ok().data("orderId",orderNo);
    }

    //根据订单id查询订单信息
    @GetMapping("getOrder/{orderId}")
    public R getOrderId(@PathVariable String orderId){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = service.getOne(wrapper);
        return R.ok().data("item",order);
    }

    //根据用户id和课程id查询课程的购买信息
    @GetMapping("isBuyCourse/{courseId}/{userId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String userId){
        if (StringUtils.isEmpty(userId)){
            return false;
        }

        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",userId);
        wrapper.eq("status",1);
        int count = service.count(wrapper);
        if (count>0){
            return true;
        }
        return false;
    }

}

