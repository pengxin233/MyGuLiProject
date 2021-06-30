import request from '@/utils/request'

export default{
    //生成订单
    createOrder(courseId){
        return request({
            url:`/eduOrder/order/createOrder/${courseId}`,
            method:'post'
        })
    },

    //根据订单id得到订单信息
    getOrder(orderId){
        return request({
            url:`/eduOrder/order/getOrder/${orderId}`,
            method:'get'
        })
    },

    //生成二维码
    createNative(orderNo){
        return request({
            url:`/eduOrder/payLog/createNative/${orderNo}`,
            method:'get'
        })
    },


    //查询订单状态
    queryPayStatus(orderNo,courseId){
        return request({
            url:`/eduOrder/payLog/queryPayStatus/${orderNo}/${courseId}`,
            method:'get'
        })
    }
}
