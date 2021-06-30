package com.px.eduOrder.controller;


import com.px.commonutils.R;
import com.px.commonutils.ResultCode;
import com.px.eduOrder.client.EduClient;
import com.px.eduOrder.service.TPayLogService;
import com.px.serviceBase.execptionHandler.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author px
 * @since 2021-06-23
 */
@RestController
@CrossOrigin
@RequestMapping("/eduOrder/payLog")
public class TPayLogController {
    @Autowired
    private TPayLogService service;

    @Autowired
    private EduClient eduClient;

    //生成微信支付二维码
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //返回相关信息，包含二维码地址还有其他需要的信息
        Map map = service.createNative(orderNo);
        System.out.println("********返回二维码的集合："+map);
        return R.ok().data(map);
    }

    //查询订单的支付状态
    @GetMapping("queryPayStatus/{orderNo}/{courseId}")
    public R queryPayStatus(@PathVariable String orderNo,@PathVariable String courseId){
        Map<String,String> map = service.queryPayStatus(orderNo);
        System.out.println("*******查询订单状态的集合："+map);
        if (map == null){
            throw new GuLiException(ResultCode.ERROR,"支付失败");
        }
        //如果不为空，通过map判断支付状态
        if (map.get("trade_state").equals("SUCCESS")){
            //添加记录到支付表，更新订单状态
            service.updateOrderStatus(map);
            //课程购买数加1
            eduClient.updateBuyCount(courseId);
            return R.ok().message("支付成功");
        }
        //25000返回码在响应拦截器中不会执行
        return R.ok().code(25000).message("支付中");
    }
}

