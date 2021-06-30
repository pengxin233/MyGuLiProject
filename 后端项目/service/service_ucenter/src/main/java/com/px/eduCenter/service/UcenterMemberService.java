package com.px.eduCenter.service;

import com.px.eduCenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.px.eduCenter.entity.vo.LoginVo;
import com.px.eduCenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author px
 * @since 2021-06-15
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    //登录
    String login(LoginVo loginVo);

    //注册
    void register(RegisterVo registerVo);
}
