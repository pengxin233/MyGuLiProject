package com.px.serviceBase.execptionHandler;

import com.px.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常管理
 */
@ControllerAdvice
@Slf4j // 表示该异常也会添加到日志
public class GlobalExecptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回json数据
    public R Error(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());// 将异常信息写入日志文件
        return R.error().message("执行了统一的异常管理"+e.getMessage());
    }


    //特定谷粒学院异常处理
    @ExceptionHandler(GuLiException.class)
    @ResponseBody //为了返回json数据
    public R Error(GuLiException e){
        e.printStackTrace();
        log.error(e.getMessage());// 将异常信息写入日志文件
        return R.error().message(e.getMsg());
    }
}
