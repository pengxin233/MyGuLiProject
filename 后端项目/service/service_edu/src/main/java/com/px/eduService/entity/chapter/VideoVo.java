package com.px.eduService.entity.chapter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//小节
@Data
public class VideoVo {
    private String id;

    private String title;
    //用于播放视频的时候通过id得到播放凭证
    private String videoSourceId;
}
