import request from '@/utils/request'

export default {
    //讲师列表，条件查询带分页
    getTeacherListPage(current,limit,teacherQuery){

        return request({
            url: `/eduService/teacher/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            //teacherQuery条件的对象,后端使用requestbody获取,使用data表示将对象转换成json进行传递到接口去
            data: teacherQuery
          })
    },
    //删除讲师
    removeTeacher(id){
        return request({
            url: `/eduService/teacher/${id}`,
            method: 'delete'
        })
    },
    //添加讲师
    addTeacher(teacher){
        return request({
            url: `/eduService/teacher/addTeacher`,
            method: 'post',
            data : teacher
        })
    },
    //根据讲师id查询
    getTeacher(id){
        return request({
            url:`/eduService/teacher/getTeacher/${id}`,
            method : 'get'
        })
    },
    //通过id进行修改
    updateTeacher(teacher){
        return request({
            url: '/eduService/teacher/updateTeacher',
            method:'post',
            data: teacher
        })
    },
    //查询所有讲师
    findAllTeacher(){
        return request({
            url:'/eduService/teacher/findAll',
            method:'get'
        })
    }
    
}
