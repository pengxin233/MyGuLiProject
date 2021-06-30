package com.px.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.px.eduService.entity.EduChapter;
import com.px.eduService.entity.EduVideo;
import com.px.eduService.entity.chapter.ChapterVo;
import com.px.eduService.entity.chapter.VideoVo;
import com.px.eduService.mapper.EduChapterMapper;
import com.px.eduService.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.eduService.service.EduVideoService;
import com.px.serviceBase.execptionHandler.GuLiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author px
 * @since 2021-03-13
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    //得到小节的service
    @Autowired
    EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideo(String courseId) {
        //根据courseId查询得到章节
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        //章节根据sort进行排序
        wrapper.orderByAsc("sort");
        List<EduChapter> chapters = baseMapper.selectList(wrapper);
//        System.out.println(chapters);

        //根据courseId查询小节
        //小节也根据sort升序
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",courseId);
        videoWrapper.orderByAsc("sort");
        List<EduVideo> videos = videoService.list(videoWrapper);
//        System.out.println(videos);

        //创建集合封装最终数据

        List<ChapterVo> list = new ArrayList<>();

        for (EduChapter chapter : chapters) {
            //转移数据到ChapterVo中
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);

            //将小节封装到对应的chapterVo中
            for (EduVideo video : videos) {
                if (video.getChapterId().equals(chapter.getId())){
                    //将video的数据封装给videoVo 再封装给chapterVo
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    chapterVo.getChildren().add(videoVo);
                }
            }
            list.add(chapterVo);
        }
        return list;
    }

    @Override
    //根据id删除章节
    public boolean deleteChapter(String chapterId) {//章节中存在小节则不能删除
        //判断是否存在小节，如果存在则不能进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        if (count>0){
            //代表章节下存在小节
            throw new GuLiException(20001,"删除失败，请先删除章节下面的小节");
        }else{
            //删除章节
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }
    }

    //根据课程id删除章节
    @Override
    public void removeByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
