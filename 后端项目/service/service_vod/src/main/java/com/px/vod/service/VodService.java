package com.px.vod.service;

import com.aliyuncs.exceptions.ClientException;
import com.px.commonutils.R;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    R uplodeAliYunVideo(MultipartFile file);

    void removeALiYunVideo(String id) throws ClientException;

    void deleteBatch(List<String> videoList) throws ClientException;
}
