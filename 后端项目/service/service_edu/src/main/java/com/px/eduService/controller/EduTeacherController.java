package com.px.eduService.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.px.commonutils.R;
import com.px.eduService.entity.EduTeacher;
import com.px.eduService.entity.vo.TeacherQuery;
import com.px.eduService.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author px
 * @since 2021-01-12
 * http://localhost:8001/eduService/edu-teacher/findAll
 */
@Api("讲师管理")//在swagger上的中文提示
@RestController
@CrossOrigin
@RequestMapping("/eduService/teacher")
public class EduTeacherController {

    @Autowired
    private EduTeacherService service;

    //查询讲师表中的所有数据
    //rest 风格

    //查询所有
    @ApiOperation("所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
        List<EduTeacher> list = service.list(null);
        return R.ok().data("items",list);
    }

    //逻辑删除讲师
    @ApiOperation("根据ID删除讲师")
    @DeleteMapping("{id}")
    //PathVariable这个注解表示得到路径的信息
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true)@PathVariable("id") String id){
        boolean b = service.removeById(id);
        return b ? R.ok():R.error();
    }


    /**
     * 分页查询
     * @param current 当前页
     * @param limit 每页记录数
     * @return
     */
    @ApiOperation("分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") long current,@PathVariable("limit") long limit){
        Page<EduTeacher> page = new Page<>(current,limit);
        service.page(page,null);

        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return R.ok().data("total",total).data("rows",records);
    }

    /**
     * 使用@RequestBody注解来得到参数的时候必须是post提交，该注解用于传递一个json对象当参数
     * @param current
     * @param limit
     * @param teacherQuery
     * @return
     */
    @ApiOperation("分页条件查询")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") long current, @PathVariable("limit") long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        R r = service.pageTeacherCondition(current, limit, teacherQuery);
        return r;
    }


    /**
     * 添加讲师
     * @param eduTeacher
     * @return
     */
    @ApiOperation("添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        service.save(eduTeacher);
        return  R.ok();
    }

    /**
     * 通过id进行查询，方便修改
     * @param id
     * @return
     */
    @ApiOperation("根据id查询，方便修改")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable("id") String id){
        EduTeacher teacher = service.getById(id);
        return R.ok().data("teacher",teacher);
    }

    @ApiOperation("同过id查询后进行修改")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = service.updateById(eduTeacher);
        return b ? R.ok() :R.error();
    }
}

