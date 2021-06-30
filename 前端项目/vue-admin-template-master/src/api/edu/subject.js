import request from '@/utils/request'

export default {
    //讲师列表，条件查询带分页
    getSubjectList(){

        return request({
            url: `/eduService/subject/getAllSubject`,
            method: 'get'
          })
    }
    
}
