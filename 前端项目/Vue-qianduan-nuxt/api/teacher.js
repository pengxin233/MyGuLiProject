import request from '@/utils/request'

export default{
    //得到讲师的分页数据
    getTeacherFrontList(page,limit){
        return request({
            url:`/eduService/teacherFront/getTeacherFrontList/${page}/${limit}`,
            method:'get'
        })
    },

    //讲师详情
    getTeacherInfo(id){
        return request({
            url:`/eduService/teacherFront/getTeacherInfo/${id}`,
            method:'get'
        })
    }
}
