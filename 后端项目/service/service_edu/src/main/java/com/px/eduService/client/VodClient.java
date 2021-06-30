package com.px.eduService.client;

import com.px.commonutils.R;
import com.px.eduService.client.impl.VodFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//VodFileDegradeFeignClient当熔断机制执行时进行方法的类
@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class) //被调用的模块在配置文件中的名字
@Component
public interface VodClient {

    //定义需要调用的方法的路径

    //删除视频 路径需要使用完全路径  PathVariable中必须指定参数名称
    @DeleteMapping("/eduVod/video/removeALiYunVideo/{id}")
    R removeALiYunVideo(@PathVariable("id") String id);

    //删除多个视频
    @DeleteMapping("/eduVod/video/delete-batch")
    R deleteBatch(@RequestParam("videoList") List<String> videoList);
}
