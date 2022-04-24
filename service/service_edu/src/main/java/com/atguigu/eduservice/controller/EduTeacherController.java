package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

//@Api(description = "讲师管理")
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin //解决跨域
public class EduTeacherController {
    //注入Service
    @Autowired
    private EduTeacherService eduTeacherService;

    //1.查询讲师表的所有数据(rest风格)
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public Result findAllTeacher() {
        //调用Service方法实现操作
        List<EduTeacher> list = eduTeacherService.list(null);
        return Result.success().data("items", list);
    }

    //逻辑删除讲师
    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("{id}")
    public Result removeTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable String id) {
        boolean flag = eduTeacherService.removeById(id);
        if (flag) {
            return Result.success();
        } else {
            return Result.failure();
        }
    }

    //分页查询
    @ApiOperation(value = "分页查询")
    @GetMapping("pageTeacher/{current}/{size}")
    public Result pageListTeacher(@ApiParam(name = "current", value = "当前页", required = true) @PathVariable long current,
                                  @ApiParam(name = "size", value = "每页显示的条数", required = true) @PathVariable long size) {
        Page<EduTeacher> page = new Page<>(current, size);
        eduTeacherService.page(page, null);
        long total = page.getTotal(); //返回总记录数
        List<EduTeacher> records = page.getRecords(); //每页数据的list集合
//        return Result.success().data("total", total).data("rows", records);
        HashMap<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", records);
        return Result.success().data(map);
    }

    //带条件的分页查询
    @ApiOperation(value = "带条件的分页查询")
    @PostMapping("pageTeacherCondition/{current}/{size}")
    public Result pageTeacherCondition(@PathVariable long current,
                                       @PathVariable long size,
                                       @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> page = new Page<>(current, size);

        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //动态拼接sql语句
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        wrapper.like(StringUtils.isNotBlank(name), "name", name).
                eq(level != null, "level", level).
                ge(StringUtils.isNotBlank(begin), "gmt_create", begin).
                le(StringUtils.isNotBlank(end), "gmt_create", end).
                orderByDesc("gmt_create");

        eduTeacherService.page(page, wrapper);

        long total = page.getTotal();
        List<EduTeacher> records = page.getRecords();
        return Result.success().data("total", total).data("rows", records);
    }

    //添加讲师
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public Result addTeacher(@RequestBody EduTeacher teacher) {
        return eduTeacherService.save(teacher) ? Result.success() : Result.failure();
    }

    //根据id查询讲师
    @ApiOperation("根据id查询讲师")
    @GetMapping("getTeacher/{id}")
    public Result getTeacherById(@PathVariable String id) {
        EduTeacher teacher = eduTeacherService.getById(id);
        return Result.success().data("teacher", teacher);
    }

    //修改讲师
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody EduTeacher teacher) {
        return eduTeacherService.updateById(teacher) ? Result.success() : Result.failure();
    }
}
