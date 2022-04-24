package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;

/**
* @author youyangxiu
* @description 针对表【edu_teacher(讲师)】的数据库操作Service
* @createDate 2022-03-24 11:27:05
*/
public interface EduTeacherService extends IService<EduTeacher> {


    Page<EduTeacher> getTeachersPage(long current, long size);
}
