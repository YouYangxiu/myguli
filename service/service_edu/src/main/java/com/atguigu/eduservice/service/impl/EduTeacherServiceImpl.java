package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author youyangxiu
 * @description 针对表【edu_teacher(讲师)】的数据库操作Service实现
 * @createDate 2022-03-24 11:27:05
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher>
        implements EduTeacherService {

    @Override
    public Page<EduTeacher> getTeachersPage(long current, long size) {
        Page<EduTeacher> page = new Page<>(current, size);
        baseMapper.selectPage(page, null);
        return page;
    }
}




