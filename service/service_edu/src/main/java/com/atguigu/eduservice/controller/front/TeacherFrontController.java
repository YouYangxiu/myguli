package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RequestMapping("/eduservice/teachersfront")
@RestController
public class TeacherFrontController {

    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    //分页查询讲师
    @GetMapping("getPageTeachers/{current}/{size}")
    public Result getPageTeachers(@PathVariable long current, @PathVariable long size) {
        Page<EduTeacher> teacherPage = teacherService.getTeachersPage(current, size);
        //总记录数
        long total = teacherPage.getTotal();

        //查询到的对象
        List<EduTeacher> teacherList = teacherPage.getRecords();
        //总页数
        long pages = teacherPage.getPages();
        //是否有上一页
        boolean hasPrevious = teacherPage.hasPrevious();
        //是否有下一页
        boolean hasNext = teacherPage.hasNext();
        //把分页的数据获取出来返回一个map集合
        HashMap<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("total", total);
        pageInfo.put("current", current);
        pageInfo.put("size", size);
        pageInfo.put("list", teacherList);
        pageInfo.put("hasPrevious", hasPrevious);
        pageInfo.put("hasNext", hasNext);
        pageInfo.put("pages", pages);
        return Result.success().data("pageInfo", pageInfo);
    }

    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId) {
        EduTeacher teacherInfo = teacherService.getById(teacherId);
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id", teacherId);
        List<EduCourse> courseInfo = courseService.list(courseQueryWrapper);
        return Result.success().data("teacherInfo", teacherInfo).data("courseInfo", courseInfo);
    }
}
