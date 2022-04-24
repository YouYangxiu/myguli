package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.Chapter;
import com.atguigu.eduservice.frontvo.CourseFrontVo;
import com.atguigu.eduservice.frontvo.CourseWebVo;
import com.atguigu.eduservice.nacos.OrdersClient;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/eduservice/coursefront")
@CrossOrigin
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    //1.条件查询带分页查询课程
    @PostMapping("getFrontCourseList/{current}/{size}")
    public Result getFrontCourseList(@PathVariable long current, @PathVariable long size, @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<EduCourse>(current, size);
        Map<String, Object> courseInfo = courseService.getFrontCoursesList(pageCourse, courseFrontVo);
        return Result.success().data("courseInfo", courseInfo);
    }

    //2.课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public Result getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        Boolean isBuy = false;
        //查询课程基本信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询课程的章节和小节部分
        List<Chapter> chaptersVideosList = chapterService.getAllTitlesAndVideosByCourseId(courseId);
        //根据课程id和用户id查询当前课程是否已经被支付
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isNotBlank(memberId)) {
            isBuy = ordersClient.isBuyCourse(courseId, memberId);
        }
        return Result.success().data("courseWebVo", courseWebVo).data("chaptersVideosList", chaptersVideosList).data("isBuy", isBuy);
    }
}
