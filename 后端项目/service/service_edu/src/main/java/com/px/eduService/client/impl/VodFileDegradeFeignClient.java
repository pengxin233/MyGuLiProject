package com.px.eduService.client.impl;

import com.px.commonutils.R;
import com.px.eduService.client.VodClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//实现类，当熔断机制执行的时候调用以下方法
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeALiYunVideo(String id) {
        return R.error().message("time out  删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoList) {
        return R.error().message("time out  删除多个视频出错");
    }
}
