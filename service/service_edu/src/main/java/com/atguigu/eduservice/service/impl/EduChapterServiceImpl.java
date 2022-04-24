package com.atguigu.eduservice.service.impl;

import com.atguigu.baseservice.exceptionhandler.GuliException;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.Chapter;
import com.atguigu.eduservice.entity.chapter.Video;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youyangxiu
 * @description 针对表【edu_chapter(课程)】的数据库操作Service实现
 * @createDate 2022-03-29 17:18:45
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter>
        implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<Chapter> getAllTitlesAndVideosByCourseId(String courseId) {
        QueryWrapper<EduChapter> chapterQueryWrapper = new QueryWrapper<>();
        QueryWrapper<EduVideo> videoQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        videoQueryWrapper.eq("course_id", courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapper);//获取所有章节
        List<EduVideo> eduVideos = eduVideoService.list(videoQueryWrapper); //获取所有小节

        List<Chapter> chapters = new ArrayList<>(); //封装此id对应的所有章，遍历每个章节，将他们的小节拿到，然后将章节加入chapters
        for (EduChapter eduChapter : eduChapters) {
            Chapter chapter = new Chapter();
            chapter.setTitle(eduChapter.getTitle());
            chapter.setId(eduChapter.getId());
            List<Video> videos = new ArrayList<>();
            for (EduVideo eduVideo : eduVideos) {
                if (eduVideo.getChapterId().equals(chapter.getId())) {
                    Video video = new Video();
                    video.setTitle(eduVideo.getTitle());
                    video.setId(eduVideo.getId());
                    video.setVideoSourceId(eduVideo.getVideoSourceId());
                    videos.add(video);
                }
            }
            //循环结束后，对应小节已经全部封装到videos，再将其封装到chapter中
            chapter.setChildren(videos);
            //封装结束，将其封装到结果集中（chapters）
            chapters.add(chapter);
        }
        return chapters;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //先查询小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        long count = eduVideoService.count(wrapper);
        if (count == 0) {
            //允许删除
            baseMapper.deleteById(chapterId);
            return true;
        } else {
            throw new GuliException(20001, "不能删除有课时的章节哦～");
        }

    }


}




