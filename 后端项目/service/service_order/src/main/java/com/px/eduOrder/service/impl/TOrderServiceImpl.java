package com.px.eduOrder.service.impl;

import com.px.commonutils.CourseWebOrder;
import com.px.commonutils.ResultCode;
import com.px.commonutils.UcenterMemberOrder;
import com.px.eduOrder.client.EduClient;
import com.px.eduOrder.client.UcenterClient;
import com.px.eduOrder.entity.TOrder;
import com.px.eduOrder.mapper.TOrderMapper;
import com.px.eduOrder.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.eduOrder.utils.OrderNoUtil;
import com.px.serviceBase.execptionHandler.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author px
 * @since 2021-06-23
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public String createOrders(String courseId, String memberId) {
        //判断是否有登录
        if (StringUtils.isEmpty(memberId)){
            throw new GuLiException(ResultCode.ERROR,"用户未登录");
        }


        //得到用户信息
        UcenterMemberOrder ucenterMember = ucenterClient.getMenberInfoById(memberId);

        //得到课程信息
        CourseWebOrder courseDto = eduClient.getCourseInfoOrder(courseId);

        //创建订单
        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName("test");
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(memberId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0); //代表是否已支付 0未支付， 1已支付
        order.setPayType(1); //支付类型 1代表微信支付
        baseMapper.insert(order);

        return order.getOrderNo();
    }
}
