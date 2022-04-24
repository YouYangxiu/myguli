package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.Result;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/eduservice/edusubject")
@CrossOrigin
@Api(tags = "课程分类管理")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    //获取上传过来的文件
    @PostMapping("addSubject")
    public Result addSubject(MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return Result.success();
    }

    //课程分类的列表功能
    @GetMapping("getAllSubjects")
    public Result getAllSubjects() {
        //针对返回的数据创建对应的实体类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return Result.success().data("list", list);
    }
}
