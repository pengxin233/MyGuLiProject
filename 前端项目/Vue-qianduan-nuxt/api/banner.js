import request from '@/utils/request'

export default{
    getList(){
        return request({
            url:'cmsService/bannerFront/getAll',
            method: 'get'
        })
    }
}