package com.atguigu.eduservice.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程科目
 * @TableName edu_subject
 */
@TableName(value ="edu_subject")
@Data
public class EduSubject implements Serializable {
    /**
     * 课程类别ID
     */
    //@TableField(value = "id")
    private String id;

    /**
     * 类别名称
     */
    @TableField(value = "title")
    private String title;

    /**
     * 父ID
     */
    @TableField(value = "parent_id")
    private String parentId;

    /**
     * 排序字段
     */
    @TableField(value = "sort")
    private Object sort;

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