import request from '@/utils/request'

export default{
    //得到视频播放凭证
    getPlayAuth(vid){
        return request({
            url:`/eduVod/video/getPlayAuth/${vid}`,
            method:'get'
        })
    }
}
