package com.atguigu.eduservice.entity.vo;

import lombok.Data;

@Data
public class TeacherQuery {
    private String name;
    private Integer level;
    private String begin; //注意，这里使用的是String类型，前端传过来的数据无需进行数据转换
    private String end;
}
