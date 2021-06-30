package com.px.eduOrder.service;

import com.px.eduOrder.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author px
 * @since 2021-06-23
 */
public interface TPayLogService extends IService<TPayLog> {

    //生成微信支付二维码
    Map createNative(String orderNo);

    //根据订单号，查询订单支付状态
    Map<String, String> queryPayStatus(String orderNo);

    //向支付表添加记录，更新订单状态
    void updateOrderStatus(Map<String, String> map);
}
