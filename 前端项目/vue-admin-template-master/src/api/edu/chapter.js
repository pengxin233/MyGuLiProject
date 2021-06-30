import request from '@/utils/request'

export default {
    //得到章节信息和小节
    getAllChapterVideo(courseId){

        return request({
            url: `/eduService/chapter/getChapterVideo/${courseId}`,
            method: 'get'
          })
    },

    //添加章节
    addChapter(eduChapter){

        return request({
            url: `/eduService/chapter/addChapter`,
            method: 'post',
            data:eduChapter
          })
    }
    ,
    //根据id查询
    findChapterById(chapterId){

        return request({
            url: `/eduService/chapter/findChapterById/`+chapterId,
            method: 'get'
          })
    },

    //修改章节
    updateChapter(chapter){

        return request({
            url: `/eduService/chapter/updateChapter`,
            method: 'post',
            data:chapter
          })
    },

    //删除章节
    deleteChapter(chapterid){

        return request({
            url: `/eduService/chapter/`+chapterid,
            method: 'delete'
          })
    }
}