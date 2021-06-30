package com.px.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.px.commonutils.R;
import com.px.vod.service.VodService;
import com.px.vod.utils.InitVodClient;
import com.px.vod.utils.VodUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    //上传视频
    @Override
    public R uplodeAliYunVideo(MultipartFile file) {

        try {
            //文件上传原始名称
            String fileName = file.getOriginalFilename();

            //文件上传后显示名称
            String title = fileName.substring(0,fileName.lastIndexOf("."));

            //file.getName() 返回参数的名字 就是file
//            System.out.println(file.getName() +"========="+file.getOriginalFilename());
            UploadStreamRequest request = new UploadStreamRequest(VodUtils.KEY_ID, VodUtils.KEY_SECRECT, title, fileName, file.getInputStream());
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            //返回视频id
            return R.ok().data("videoId",response.getVideoId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeALiYunVideo(String id) throws ClientException {
        //初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient(VodUtils.KEY_ID, VodUtils.KEY_SECRECT);

        //创建删除视频的request
        DeleteVideoRequest request = new DeleteVideoRequest();

        //设置视频id
        request.setVideoIds(id);

        //调用初始化的方法删除

        client.getAcsResponse(request);
    }

    @Override
    public void deleteBatch(List<String> videoList) throws ClientException {
        //初始化对象
        DefaultAcsClient client = InitVodClient.initVodClient(VodUtils.KEY_ID, VodUtils.KEY_SECRECT);

        //创建删除视频的request
        DeleteVideoRequest request = new DeleteVideoRequest();

        //拼接list，吧id用逗号隔开
        String join = StringUtil.join(videoList.toArray(), ",");

        //设置视频id
        request.setVideoIds(join);

        //调用初始化的方法删除
        client.getAcsResponse(request);
    }
}
