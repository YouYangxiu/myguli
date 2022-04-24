package com.atguigu.eduservice.service.impl;

import com.atguigu.baseservice.exceptionhandler.GuliException;
import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.*;
import com.atguigu.eduservice.entity.vo.CourseInfo;
import com.atguigu.eduservice.entity.vo.CoursePublish;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.frontvo.CourseFrontVo;
import com.atguigu.eduservice.frontvo.CourseWebVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.nacos.VodClient;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author youyangxiu
 * @description 针对表【edu_course(课程)】的数据库操作Service实现
 * @createDate 2022-03-29 17:18:45
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse>
        implements EduCourseService {

    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Autowired
    private EduVideoService videoService;
    @Autowired
    private EduChapterService chapterService;

    @Override
    public String saveCourseInfo(CourseInfo courseInfo) {
        //向课程表添加课程信息
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseInfo.getId());
        eduCourse.setPrice(courseInfo.getPrice());
        eduCourse.setLessonNum(courseInfo.getLessonNum());
        eduCourse.setSubjectId(courseInfo.getSubjectId());
        eduCourse.setTeacherId(courseInfo.getTeacherId());
        eduCourse.setCover(courseInfo.getCover());
        eduCourse.setTitle(courseInfo.getTitle());
        eduCourse.setSubjectParentId(courseInfo.getSubjectParentId());
        int insert = baseMapper.insert(eduCourse);
        if (insert <= 0) {
            throw new GuliException(20001, "课程添加失败");
        }

        //获取id
        String id = eduCourse.getId();
        //向课程简介表添加课程描述
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(id);
        eduCourseDescription.setDescription(courseInfo.getDescription());
        descriptionService.save(eduCourseDescription);
        return id;
    }

    @Override
    public CourseInfo getCourseById(String courseId) {
        EduCourse eduCourse = this.getById(courseId);
        CourseInfo courseInfo = new CourseInfo();
        BeanUtils.copyProperties(eduCourse, courseInfo);
        EduCourseDescription courseDescription = descriptionService.getById(courseId);
        if (courseDescription != null) {
            courseInfo.setDescription(courseDescription.getDescription());
        }
        return courseInfo;
    }

    @Override
    public String updateCourseInfo(CourseInfo courseInfo) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        int result = baseMapper.updateById(eduCourse);
        if (result <= 0) {
            throw new GuliException(20001, "修改课程信息失败！");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfo.getDescription());
        eduCourseDescription.setId(eduCourse.getId());
        descriptionService.updateById(eduCourseDescription);
        return courseInfo.getId();
    }

    @Override
    public CoursePublish coursePublishInfo(String courseId) {
        CoursePublish coursePublish = baseMapper.getPublishCourseInfo(courseId);
        return coursePublish;
    }

    @Override
    public Page<EduCourse> getPagedCourses(Long current, Long size, CourseQuery courseQuery) {
        Page<EduCourse> eduCoursePage = new Page<>(current, size);
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.like(StringUtils.isNotBlank(courseQuery.getCourseTitle()), "title", courseQuery.getCourseTitle()).
                eq(courseQuery.getLessonNum() != null, "lesson_num", courseQuery.getLessonNum()).
                ge(courseQuery.getPriceBegin() != null, "price", courseQuery.getPriceBegin()).
                le(courseQuery.getPriceEnd() != null, "price", courseQuery.getPriceEnd());
        this.page(eduCoursePage, eduCourseQueryWrapper);
        return eduCoursePage;
    }

    @Autowired
    private VodClient vodClient;

    @Override
    public boolean deleteCourseById(String courseId) {
        //删除课程描述、章节、小节
        //先删除小节
        //查询所有视频id

        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId).select("video_source_id");
        List<EduVideo> ids = videoService.list(videoQueryWrapper);
        List<String> videoIds = new ArrayList<>();
        for (EduVideo id : ids) {
            String sourceId = id.getVideoSourceId();
            if (!StringUtils.isEmpty(sourceId)) {
                videoIds.add(sourceId);
            }
        }
        if (videoIds.size() > 0) {
            vodClient.deleteAliyunVideBatch(videoIds);
        }
        videoService.remove(videoQueryWrapper);
        //删除章节
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        chapterService.remove(chapterQueryWrapper);
        //删除课程信息
        QueryWrapper<EduCourseDescription> descriptionQueryWrapper = new QueryWrapper<>();
        descriptionQueryWrapper.eq("id", courseId);
        descriptionService.remove(descriptionQueryWrapper);
        //删除课程
        baseMapper.deleteById(courseId);
        return true;
    }

    @Override
    public Map<String, Object> getFrontCoursesList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.
                eq(StringUtils.isNotBlank(courseFrontVo.getSubjectParentId()), "subject_parent_id", courseFrontVo.getSubjectParentId()).
                eq(StringUtils.isNotBlank(courseFrontVo.getSubjectId()), "subject_id", courseFrontVo.getSubjectId()).
                orderByDesc(StringUtils.isNotBlank(courseFrontVo.getPriceSort()), "price").
                orderByDesc(StringUtils.isNotBlank(courseFrontVo.getGmtCreateSort()), "gmt_create").
                orderByDesc(StringUtils.isNotBlank(courseFrontVo.getBuyCountSort()), "buy_count");
        Page<EduCourse> coursePage = baseMapper.selectPage(pageCourse, courseQueryWrapper);
        long total = coursePage.getTotal();
        //查询到的对象
        List<EduCourse> courseList = coursePage.getRecords();
        //总页数
        long pages = coursePage.getPages();
        //是否有上一页
        boolean hasPrevious = coursePage.hasPrevious();
        //是否有下一页
        boolean hasNext = coursePage.hasNext();
        //当前页
        long current = coursePage.getCurrent();
        //每页的数据数
        long size = coursePage.getSize();
        //把分页的数据获取出来返回一个map集合
        HashMap<String, Object> pageInfo = new HashMap<>();
        pageInfo.put("total", total);
        pageInfo.put("current", current);
        pageInfo.put("size", size);
        pageInfo.put("list", courseList);
        pageInfo.put("hasPrevious", hasPrevious);
        pageInfo.put("hasNext", hasNext);
        pageInfo.put("pages", pages);
        return pageInfo;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }

}




