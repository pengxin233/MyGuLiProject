import request from '@/utils/request'

export default {
    //添加课程信息
    addCourseInfo(courseInfo){

        return request({
            url: `/eduService/course/addCourseInfo`,
            method: 'post',
            data:courseInfo
          })
    },

    //根据课程id查询
    getCourseInfoById(courseId){
        return request({
            url:'/eduService/course/getCourseInfoById/'+courseId,
            method:'get'
        })
    },

    //修改课程信息
    updateCourseInfo(courseInfo){
        return request({
            url:'/eduService/course/updateCourseInfo',
            method:'post',
            data:courseInfo
        })
    },

    //根据id查询课程确认信息
    getPublishCourseInfo(id){
        return request({
            url:'/eduService/course/getPublishCourseInfo/'+id,
            method:'get'
        })
    },
    //课程最终发布
    
    publishCourse(id){
        return request({
            url:'/eduService/course/publishCourse/'+id,
            method:'post'
        })
    },

    //课程信息的分页条件查询
    pageCourseCondition(current,limit,courseQuery){
        return request({
            url:`/eduService/course/pageCourseCondition/${current}/${limit}`,
            method:'post',
            data:courseQuery
        })
    },
    //删除课程信息
    deleteCourse(id){
        return request({
            url:'/eduService/course/'+id,
            method:'delete'
        })
    }
}