package com.atguigu.eduservice.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程简介
 * @TableName edu_course_description
 */
@TableName(value ="edu_course_description")
@Data
public class EduCourseDescription implements Serializable {
    /**
     * 课程ID
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 课程简介
     */
    @TableField(value = "description")
    private String description;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    private Date gmtCreate;

    /**
     * 更新时间
     */
    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}