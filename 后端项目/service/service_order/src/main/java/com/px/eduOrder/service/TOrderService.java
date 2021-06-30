package com.px.eduOrder.service;

import com.px.eduOrder.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author px
 * @since 2021-06-23
 */
public interface TOrderService extends IService<TOrder> {

    //生成订单，返回订单号
    String createOrders(String courseId, String memberId);
}
