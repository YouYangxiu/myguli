package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CoursePublish;
import com.atguigu.eduservice.frontvo.CourseWebVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @author youyangxiu
 * @description 针对表【edu_course(课程)】的数据库操作Mapper
 * @createDate 2022-03-29 17:18:45
 * @Entity com.atguigu.com.atguigu.eduservice.entity.EduCourse
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublish getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}




