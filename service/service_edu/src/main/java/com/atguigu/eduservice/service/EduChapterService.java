package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author youyangxiu
 * @description 针对表【edu_chapter(课程)】的数据库操作Service
 * @createDate 2022-03-29 17:18:45
 */
public interface EduChapterService extends IService<EduChapter> {

    List<Chapter> getAllTitlesAndVideosByCourseId(String courseId);

    boolean deleteChapter(String chapterId);
}
