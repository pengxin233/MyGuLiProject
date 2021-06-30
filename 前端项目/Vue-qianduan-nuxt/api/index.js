import request from '@/utils/request'

export default{
    getIndexData(){
        return request({
            url:'eduService/indexFront/index',
            method:'get'
        })
    }
}