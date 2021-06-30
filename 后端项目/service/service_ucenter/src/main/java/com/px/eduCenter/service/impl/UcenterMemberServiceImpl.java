package com.px.eduCenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.px.commonutils.JwtUtils;
import com.px.commonutils.MD5;
import com.px.commonutils.ResultCode;
import com.px.eduCenter.entity.UcenterMember;
import com.px.eduCenter.entity.vo.LoginVo;
import com.px.eduCenter.entity.vo.RegisterVo;
import com.px.eduCenter.mapper.UcenterMemberMapper;
import com.px.eduCenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.px.serviceBase.execptionHandler.GuLiException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author px
 * @since 2021-06-15
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public String login(LoginVo loginVo) {
        //获取手机号和密码
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        //判断手机号和密码不能为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new GuLiException(ResultCode.ERROR,"登录失败,手机号和密码不能为空");
        }

        //判断手机号是否正确, 查询该手机号的信息
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);

        //判断查询出来的对象是否为空
        if (ucenterMember == null){
            throw new GuLiException(ResultCode.ERROR,"登录失败,手机号未被注册");
        }

        //密码是否正确
        //存储到数据库的密码已进行加密，对密码进行加密
        password = MD5.encrypt(password);
        if (!password.equals(ucenterMember.getPassword())){
            throw new GuLiException(ResultCode.ERROR,"登录失败,密码错误");
        }
        //判断用户是否被禁用
        if (ucenterMember.getIsDisabled()){
            throw new GuLiException(ResultCode.ERROR,"登录失败，该用户已被禁用");
        }

        //登录成功,生成token字符串
        String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return jwtToken;
    }

    @Override
    //注册
    public void register(RegisterVo registerVo) {
        String code = registerVo.getCode();

        String mobile = registerVo.getMobile();

        String nickname = registerVo.getNickname();

        String password = registerVo.getPassword();

        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) ||
                StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password) ){
            throw new GuLiException(ResultCode.ERROR,"注册失败，所填不能为空");
        }

        //从Redis中得到验证码
        String rcode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(rcode)){
            throw new GuLiException(ResultCode.ERROR,"注册失败，验证码错误");
        }

        //手机号不能重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper); //返回值为得到数据行数

        if (count > 0){
            throw new GuLiException(ResultCode.ERROR,"注册失败，手机号已存在，请直接登录");
        }

        //最终添加
        UcenterMember member = new UcenterMember();
        member.setNickname(registerVo.getNickname());
        member.setMobile(registerVo.getMobile());
        member.setPassword(MD5.encrypt(registerVo.getPassword()));
        member.setIsDisabled(false);
        member.setAvatar("https://edu-1011010.oss-cn-beijing.aliyuncs.com/2021/03/10/ee82196e1da14da1ac4529e4e3ba478afile.png");
        baseMapper.insert(member);
    }
}
