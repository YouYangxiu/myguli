package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.commonutils.ordervo.CourseWebOrder;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfo;
import com.atguigu.eduservice.entity.vo.CoursePublish;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/eduservice/educourse")
@RestController
@CrossOrigin
@Api(tags = "课程管理")
public class EduCourseController {

    @Autowired
    private EduCourseService service;

    @PostMapping("addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfo courseInfo) {
        String id = service.saveCourseInfo(courseInfo);
        return Result.success().data("courseId", id);
    }

    //在后面的页面点击上一步之后根据courseId回显信息
    @GetMapping("getCourseById/{courseId}")
    public Result getCourseById(@PathVariable String courseId) {
        CourseInfo courseInfo = service.getCourseById(courseId);
        return Result.success().data("courseInfo", courseInfo);
    }


    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfo courseInfo) {
        String id = service.updateCourseInfo(courseInfo);
        return Result.success().data("courseId", id);
    }

    //根据课程id查询所有的发布课程信息
    @GetMapping("/getCoursePublishInfo/{courseId}")
    public Result getCoursePublishInfo(@PathVariable String courseId) {
        CoursePublish coursePublish = service.coursePublishInfo(courseId);
        return Result.success().data("result", coursePublish);
    }

    //课程最终发布
    @PostMapping("/publishCourse/{courseId}")
    public Result publishCourse(@PathVariable String courseId) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        service.updateById(eduCourse);
        return Result.success();
    }

    //条件查询带分页
    @PostMapping("getPageCourses/{current}/{size}")
    public Result getPageCourses(@PathVariable Long current,
                                 @PathVariable Long size,
                                 @RequestBody(required = false) CourseQuery courseQuery) {
        Page<EduCourse> page = service.getPagedCourses(current, size, courseQuery);
        return Result.success().data("total", page.getTotal()).data("rows", page.getRecords());
    }

    //根据id删除课程
    @DeleteMapping("deleteCourseById/{courseId}")
    public Result deleteCourseById(@PathVariable String courseId) {
        boolean result = service.deleteCourseById(courseId);
        return result ? Result.success() : Result.failure();
    }

    //根据课程id查询课程信息
    @GetMapping("getCourseInfoOrder/{id}")
    public CourseWebOrder getCourseInfoOrder(@PathVariable String id) {
        EduCourse eduCourse = service.getById(id);
        CourseWebOrder courseWebOrder = new CourseWebOrder();
        BeanUtils.copyProperties(eduCourse, courseWebOrder);
        return courseWebOrder;
    }
}
