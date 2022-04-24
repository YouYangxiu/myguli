package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.Chapter;
import com.atguigu.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/eduservice/educhapter")
@RestController
@CrossOrigin
@Api(tags = "章节管理")
public class EduChapterController {
    @Autowired
    private EduChapterService service;

    @GetMapping("getTitlesByCourseId/{courseId}")
    public Result getAllTitlesAndVideos(@PathVariable String courseId) {
        List<Chapter> result = service.getAllTitlesAndVideosByCourseId(courseId);
        return Result.success().data("result", result);
    }

    //添加章节
    @PostMapping("addChapter")
    public Result addChapter(@RequestBody EduChapter chapter) {
        service.save(chapter);
        return Result.success();
    }

    //修改章节信息
    @PostMapping("updateChapter")
    public Result updateChapter(@RequestBody EduChapter chapter) {
        service.updateById(chapter);
        return Result.success();
    }

    //根据章节id查询章节信息
    @GetMapping("getChapter/{chapterId}")
    public Result getChapterById(@PathVariable String chapterId) {
        EduChapter chapter = service.getById(chapterId);
        return Result.success().data("chapter", chapter);
    }

    //根据章节id删除章节
    @DeleteMapping("deleteChapterById/{chapterId}")
    public Result deleteChapterById(@PathVariable String chapterId) {
        boolean result = service.deleteChapter(chapterId);
        return result ? Result.success().message("删除成功！") : Result.success().message("删除失败！");
    }
}
