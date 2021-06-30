package com.px.eduService.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.commonutils.JwtUtils;
import com.px.commonutils.R;
import com.px.commonutils.ResultCode;
import com.px.eduService.entity.EduComment;
import com.px.eduService.service.EduCommentService;
import com.px.serviceBase.execptionHandler.GuLiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author px
 * @since 2021-06-22
 */
@RestController
@CrossOrigin
@RequestMapping("/eduService/comment")
public class EduCommentController {

    @Autowired
    private EduCommentService service;

    //添加评论
    @PostMapping("saveCompent")
    public R saveCompent(@RequestBody EduComment comment , HttpServletRequest request){
        //        通过jwt判断用户是否登录
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberId)){
            throw new GuLiException(ResultCode.ERROR,"用户未登录");
        }

        service.save(comment);
        return R.ok();
    }

    //得到分页数据
    @GetMapping("{page}/{limit}")
    public R getPage(@PathVariable Long page,@PathVariable Long limit,String courseId){
        Page<EduComment> pageParam = new Page<>(page,limit);

        Map<String,Object> map = service.selectPage(pageParam,courseId);
        return R.ok().data(map);
    }
}

