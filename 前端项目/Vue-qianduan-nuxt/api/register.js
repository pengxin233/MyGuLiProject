import request from '@/utils/request'

export default{
    //根据手机号发送验证码
    sendCode(phone){
        return request({
            url:`/eduMsm/msm/send/${phone}`,
            method:'get'
        })
    },

    //注册
    registerMember(formItem){
        return request({
            url:`/eduCenter/member/register`,
            method:'post',
            data: formItem
        })
    }
}
