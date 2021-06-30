import request from '@/utils/request'

export default{
    //添加课程评论
    saveCompent(comment){
        return request({
            url:'/eduService/comment/saveCompent',
            method:'post',
            data:comment
        })
    },
    //得到课程评论分页
    getPageList(page,limit,courseId){
        return request({
            url:`/eduService/comment/${page}/${limit}`,
            method:'get',
            params:{courseId}
        })
    }
}
