package com.px.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.px.oss.service.OssService;
import com.px.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // 工具类获取值
        String endpoint = ConstantPropertiesUtils.END_POINT;
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketname = ConstantPropertiesUtils.BUCKET_NAME;
        OSS ossClient = null;

        try {
// 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 获取文件上传输入流
            InputStream inputStream = file.getInputStream();

            //获取文件名称,通过方法可以获得文件原来的名称
            String filename = file.getOriginalFilename();
            //对文件名进行去重
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename = uuid+filename;

            //对文件进行根据时间分类
            //使用工具类得到当前时间
            String datePath = new DateTime().toString("yyyy/MM/dd");

            filename = datePath +"/"+ filename;

// 调用方法实现上传
            ossClient.putObject(bucketname, filename, inputStream);
            //将上传到阿里云的路径手动拼接处理返回
            //https://edu-1011010.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://"+bucketname+"."+endpoint+"/"+filename;
            return url;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭OSSClient。
            ossClient.shutdown();
        }
        return null;
    }
}
