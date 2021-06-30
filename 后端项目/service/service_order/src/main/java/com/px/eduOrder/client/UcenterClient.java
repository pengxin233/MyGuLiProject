package com.px.eduOrder.client;

import com.px.commonutils.R;
import com.px.commonutils.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
    //通过用户id得到用户信息
    @GetMapping("/eduCenter/member/getMenberInfoById/{id}")
    UcenterMemberOrder getMenberInfoById(@PathVariable("id") String id);
}
