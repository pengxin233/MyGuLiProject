package com.px.eduService.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.px.commonutils.R;
import com.px.eduService.client.VodClient;
import com.px.eduService.entity.EduVideo;
import com.px.eduService.mapper.EduVideoMapper;
import com.px.eduService.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.serviceBase.execptionHandler.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author px
 * @since 2021-03-13
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    VodClient vodClient;

    @Override
    //根据课程id删除小节
    public void removeByCourseId(String courseId) {
        //根据课程id查询出所有视频id
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        videoQueryWrapper.select("video_source_id"); //只查询指定字段
        List<EduVideo> videos = baseMapper.selectList(videoQueryWrapper);

        //List<EduVideo> ==> List<String>
        List<String> list = new ArrayList<>();

        for (EduVideo video : videos) {
            //当视频id不为空是放入list进行删除
            if (!StringUtils.isEmpty(video.getVideoSourceId())){
                list.add(video.getVideoSourceId());
            }
        }
        //调用VOd中的批量删除方法,删除课程中的多个视频
        //当list不为空是进行删除
        if (!list.isEmpty()){
            R r = vodClient.deleteBatch(list);

            if (r.getCode() == 20001){
                throw new GuLiException(20001,"删除多个视频失败，熔断器...");
            }

        }

        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }

    //根据小节id进行删除
    @Override
    public void removeVideoById(String id) {
        //根据小节id得到视频id
        EduVideo eduVideo = baseMapper.selectById(id);

        //如果eduVideo中没有视频 id则不需要删除
        if (!StringUtils.isEmpty(eduVideo.getVideoSourceId())){
            //删除小节中的视频
            R r = vodClient.removeALiYunVideo(eduVideo.getVideoSourceId());

            if (r.getCode() == 20001){
                throw new GuLiException(20001,"删除视频失败，熔断器...");
            }

        }
        //删除小节
        baseMapper.deleteById(id);
    }
}
