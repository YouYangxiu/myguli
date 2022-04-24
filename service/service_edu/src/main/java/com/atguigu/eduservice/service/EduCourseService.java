package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfo;
import com.atguigu.eduservice.entity.vo.CoursePublish;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.frontvo.CourseFrontVo;
import com.atguigu.eduservice.frontvo.CourseWebVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author youyangxiu
 * @description 针对表【edu_course(课程)】的数据库操作Service
 * @createDate 2022-03-29 17:18:45
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfo courseInfo);

    CourseInfo getCourseById(String courseId);

    String updateCourseInfo(CourseInfo courseInfo);

    CoursePublish coursePublishInfo(String courseId);

    Page<EduCourse> getPagedCourses(Long current, Long size, CourseQuery courseQuery);

    boolean deleteCourseById(String courseId);

    Map<String, Object> getFrontCoursesList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
