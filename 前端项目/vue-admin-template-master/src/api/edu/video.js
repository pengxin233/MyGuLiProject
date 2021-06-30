import request from '@/utils/request'

export default {
    //添加小节
    addVideo(eduVideo){

        return request({
            url: `/eduService/video/addVideo`,
            method: 'post',
            data:eduVideo
          })
    },

    //删除小节
    deleteVideo(id){
        return request({
            url: `/eduService/video/`+id,
            method: 'delete'
          })
    },

    //修改小节
    updateVideo(eduVideo){
        return request({
            url: `/eduService/video/updateVideo`,
            method: 'post',
            data:eduVideo
          })
    },

    //根据id查询小节
    findVideoById(id){
        return request({
            url: `/eduService/video/findVideoById/`+id,
            method: 'get'
          })
    },

    //根据视频id删除阿里云的视频
    removeALiYunVideo(id){
        return request({
            url: `/eduVod/video/removeALiYunVideo/`+id,
            method: 'delete'
          })
    }

    
}
