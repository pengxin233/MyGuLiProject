import request from '@/utils/request'

export default{
    //得到课程的条件带分页数据
    getFrontCourseList(page,limit,searchObj){
        return request({
            url:`/eduService/courseFront/getFrontCourseList/${page}/${limit}`,
            method:'post',
            data:searchObj
        })
    },
    //查询一级二级分类
    getAllSubject(){
        return request({
            url:'/eduService/subject/getAllSubject',
            method:'get'
        })
    },
    //查询课程信息
    getFrontCourseInfo(courseId){
        return request({
            url:`/eduService/courseFront/getFrontCourseInfo/${courseId}`,
            method:'get'
        })
    },
    //浏览课程后更新课程的浏览数
    updateViewCount(courseId){
        return request({
            url:`/eduService/courseFront/updateViewCount/${courseId}`,
            method:'put'
        })
    },
    //支付成功后更新课程的购买数
    updateBuyCount(courseId){
        return request({
            url:`/eduService/courseFront/updateBuyCount/${courseId}`,
            method:'put'
        })
    }
}
