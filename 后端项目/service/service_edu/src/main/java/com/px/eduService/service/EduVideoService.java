package com.px.eduService.service;

import com.px.eduService.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author px
 * @since 2021-03-13
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeByCourseId(String courseId);

    void removeVideoById(String id);
}
