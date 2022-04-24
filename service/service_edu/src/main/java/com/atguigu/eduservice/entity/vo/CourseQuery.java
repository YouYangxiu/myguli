package com.atguigu.eduservice.entity.vo;

import lombok.Data;

@Data
public class CourseQuery {
    private String courseTitle;
    private Long lessonNum;
    private Long priceBegin;
    private Long priceEnd;
}
