package com.px.eduCenter.controller;


import com.px.commonutils.JwtUtils;
import com.px.commonutils.R;
import com.px.commonutils.UcenterMemberOrder;
import com.px.eduCenter.entity.UcenterMember;
import com.px.eduCenter.entity.vo.LoginVo;
import com.px.eduCenter.entity.vo.RegisterVo;
import com.px.eduCenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author px
 * @since 2021-06-15
 */
@RestController
@RequestMapping("/eduCenter/member")
@CrossOrigin
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService service;

    //登录
    @PostMapping("login")
    //传递对象只能用post
    public R login(@RequestBody LoginVo loginVo){
        //返回token值
        String token = service.login(loginVo);
        return R.ok().data("token",token);
    }

    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo){
        service.register(registerVo);
        return R.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类通过request头对象得到用户id
        String id = JwtUtils.getMemberIdByJwtToken(request);

        //调用数据库，根据id得到用户信息

        UcenterMember member = service.getById(id);
        return R.ok().data("memberInfo",member);
    }

    //通过用户id得到用户信息
    @GetMapping("getMenberInfoById/{id}")
    public UcenterMemberOrder getMenberInfoById(@PathVariable String id){
        UcenterMember member = service.getById(id);
        UcenterMemberOrder order = new UcenterMemberOrder();

        BeanUtils.copyProperties(member,order);
        return order;
    }
}

