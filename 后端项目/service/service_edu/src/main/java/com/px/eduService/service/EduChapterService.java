package com.px.eduService.service;

import com.px.eduService.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.eduService.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author px
 * @since 2021-03-13
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideo(String courseId);

    boolean deleteChapter(String chapterId);

    void removeByCourseId(String courseId);
}
