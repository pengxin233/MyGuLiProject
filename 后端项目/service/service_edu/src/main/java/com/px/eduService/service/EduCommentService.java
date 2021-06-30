package com.px.eduService.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.eduService.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author px
 * @since 2021-06-22
 */
public interface EduCommentService extends IService<EduComment> {

    //显示分页数据
    Map<String, Object> selectPage(Page<EduComment> pageParam, String courseId);
}
