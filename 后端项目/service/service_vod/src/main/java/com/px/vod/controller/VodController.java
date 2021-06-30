package com.px.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.px.commonutils.R;
import com.px.commonutils.ResultCode;
import com.px.serviceBase.execptionHandler.GuLiException;
import com.px.vod.service.VodService;
import com.px.vod.utils.InitVodClient;
import com.px.vod.utils.VodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduVod/video")
public class VodController {

    @Autowired
    VodService service;

    //上传视频到阿里云
    @PostMapping("uplodeAliYunVideo")
    public R uplodeAliYunVideo(MultipartFile file){
        R r = service.uplodeAliYunVideo(file);
        return r;
    }

    //删除视频
    @DeleteMapping("removeALiYunVideo/{id}")
    public R removeALiYunVideo(@PathVariable String id){
        try {
            service.removeALiYunVideo(id);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuLiException(20001,"删除视频失败");
        }
    }

    //删除多个视频
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoList") List<String> videoList){
        try {
            service.deleteBatch(videoList);
            return R.ok();
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuLiException(20001,"批量删除视频失败");
        }
    }

    //获取视频播放凭证
    @GetMapping("getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId){
        //初始化
        DefaultAcsClient client = null;
        try {
            //获取阿里云存储相关常量
            String accessKeyId = VodUtils.KEY_ID;
            String accessKeySecret = VodUtils.KEY_SECRECT;
            client = InitVodClient.initVodClient(accessKeyId, accessKeySecret);

            //请求
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId(videoId);

            //响应
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);

            //得到播放凭证
            String playAuth = response.getPlayAuth();

            //返回结果
            return R.ok().message("获取凭证成功").data("playAuth", playAuth);

        } catch (ClientException e) {
            throw new GuLiException(ResultCode.ERROR,e.getMessage());
        }
    }
}
