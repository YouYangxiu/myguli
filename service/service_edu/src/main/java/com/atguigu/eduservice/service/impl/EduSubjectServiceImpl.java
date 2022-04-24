package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author youyangxiu
 * @description 针对表【edu_subject(课程科目)】的数据库操作Service实现
 * @createDate 2022-03-28 09:52:42
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService subjectService) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        List<OneSubject> oneSubjectArrayList = new ArrayList<>();
        //先查询一级分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", 0);
        List<EduSubject> oneSubjects = this.list(wrapper);
        //由一级分类查询二级分类并封装
        for (EduSubject oneSubject : oneSubjects) {
            //先封装一级分类
            OneSubject oneSubjectPackage = new OneSubject();
            oneSubjectPackage.setId(oneSubject.getId());
            oneSubjectPackage.setTitle(oneSubject.getTitle());
            //查询二级分类
            QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
            wrapperTwo.eq("parent_id", oneSubject.getId());
            List<EduSubject> list = this.list(wrapperTwo);
            //封装二级分类
            List<TwoSubject> twoSubjects = new ArrayList<>();
            for (EduSubject subject : list) {
                TwoSubject twoSubject = new TwoSubject();
                twoSubject.setId(subject.getId());
                twoSubject.setTitle(subject.getTitle());
                twoSubjects.add(twoSubject);
            }
            //将二级分类塞到一级分类下的children属性
            oneSubjectPackage.setChildren(twoSubjects);
            oneSubjectArrayList.add(oneSubjectPackage);
        }
        return oneSubjectArrayList;
    }
}




