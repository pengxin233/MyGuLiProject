package com.px.msmservice.controller;

import com.px.commonutils.R;
import com.px.commonutils.ResultCode;
import com.px.msmservice.service.MsmService;
import com.px.msmservice.utils.RandomUtil;
import com.px.serviceBase.execptionHandler.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/eduMsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService service;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;//spring封装的Redis对象

    @GetMapping("send/{phone}")
    //通过设置Redis的有效时间可以设置验证码多长时间有效
    public R sentMsm(@PathVariable String phone){
        //从Redis中获取验证码
        String code = redisTemplate.opsForValue().get(phone);
        //如果Redis中存在验证码，代表验证码还未过期
        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }

        //生成随机值验证码，给腾讯云进行发送
        code = RandomUtil.getFourBitRandom();

        //调用service里面方法进行短信发送
        boolean isSend = service.send(code,phone);
        if (isSend){
            //发送成功则将验证码存入Redis中，并设置过期时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES); //设置过期时间为5分钟
            return R.ok();
        }else {
            GuLiException e = new GuLiException();
            e.setCode(ResultCode.ERROR);
            e.setMsg("短信发送失败");
            throw e;
        }
    }
}
