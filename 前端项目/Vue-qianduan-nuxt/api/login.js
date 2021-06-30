import request from '@/utils/request'

export default{
    submitLogin(formItem){
        return request({
            url:'/eduCenter/member/login',
            method:'post',
            data:formItem
        })
    },

    //获取用户信息
    getMemberInfo(){
        return request({
            url:'/eduCenter/member/getMemberInfo',
            method:'get'
        })
    }
}